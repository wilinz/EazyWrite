package com.eazywrite.app.ui.image_editing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eazywrite.app.R;
import com.eazywrite.app.databinding.ActivityImageEditingBinding;
import com.eazywrite.app.util.ActivityKt;
import com.eazywrite.app.util.MediaUtil;
import com.eazywrite.app.util.PermissionUtil;

import java.io.File;

public class ImageEditingActivity extends AppCompatActivity {

    ActivityImageEditingBinding binding;
    ImageEditingViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initView();
        observerDataStateUpdateAction();
    }

    public class Click{
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void editImage(View view){
            vm.dewarpImage();
        }
    }

    /**
     * 处理拍摄结果 和 从相册选择结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case MediaUtil.TAKE_PHOTO:
                    File imageFile = MediaUtil.getInstance().getImage();
                    if (resultCode == RESULT_OK) {
                        //成功拍摄
                        vm.imageFile.setValue(imageFile);
                    } else {
                        //未拍摄,删除提前创建的文件
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                        Toast.makeText(ImageEditingActivity.this, "未进行拍摄", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                case MediaUtil.CHOOSE_PHOTO:
                    if (resultCode == RESULT_OK && data != null) {
                        MediaUtil.handleImage(this, data);//处理返回intent中的image 以得到相片的绝对路径imagePath
                        File ImageFile = MediaUtil.getInstance().getImage();
                        vm.imageFile.setValue(ImageFile);
                    } else {
                        Toast.makeText(ImageEditingActivity.this, "未选择图片", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {//MediaUtil.getInstance().getImage()图片为空异常
            Toast.makeText(ImageEditingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 处理权限请求结果
     *
     * @param requestCode  The request code passed in {@link # requestPermissions(
     *                     android.app.Activity, String[], int)}
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtil.WRITE_EXTERNAL_STORAGE:
            case PermissionUtil.READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    vm.chosePhone(this, this);
                } else {
                    Toast.makeText(ImageEditingActivity.this, "您拒绝了使用相册", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case PermissionUtil.CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    vm.takePhoto(this, this);
                } else {
                    Toast.makeText(ImageEditingActivity.this, "您拒绝了使用相机", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    /**
     * 为视图设置初值
     */
    private void initView() {
        //弹出选择图片导入方式对话框
        AlertDialog.Builder dialog = new AlertDialog.Builder(ImageEditingActivity.this);
        dialog.setTitle("导入图片");
        dialog.setMessage("请选择导入方式");
        dialog.setCancelable(false);
        dialog.setPositiveButton("拍照", (dialogInterface, i) -> vm.requestPermissions(true, this, this));
        dialog.setNegativeButton("从相册选择", (dialogInterface, i) -> vm.requestPermissions(false, this, this));
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount()==0)
                finish();
            return false;
        });
        dialog.show();
    }

    /**
     * 监测数据变化
     */
    private void observerDataStateUpdateAction() {
        vm.imageFile.observe(this, file -> Glide.with(ImageEditingActivity.this).load(file)
                .skipMemoryCache(true)//不做内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存到disk硬盘中
                .override(2000, 2000)
                .into(binding.imageView));
        vm.editedImage.observe(this, file -> Glide.with(ImageEditingActivity.this).load(file)
                .skipMemoryCache(true)//不做内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存到disk硬盘中
                .override(2000, 2000)
                .into(binding.imageView));
    }

    /**
     * 初始化DataBinding与ViewModel
     */
    private void initActivity() {
        ActivityKt.setWindow(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_editing);
        vm = new ViewModelProvider(this).get(ImageEditingViewModel.class);
        binding.setClick(new Click());
        binding.setLifecycleOwner(this);
    }
}