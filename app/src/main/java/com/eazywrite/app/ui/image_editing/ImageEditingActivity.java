package com.eazywrite.app.ui.image_editing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
                    if (resultCode == RESULT_OK) {
                        //成功拍摄
                        File imageFile = MediaUtil.getInstance().getImage();
                        RoundedCorners roundedCorners = new RoundedCorners(30);//设置圆角大小
                        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                        Glide.with(this).load(imageFile).apply(options).into(binding.imageView);
                    } else {
                        //未拍摄,删除提前创建的文件
                        File imageFile = MediaUtil.getInstance().getImage();
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                        Toast.makeText(ImageEditingActivity.this, "未进行拍摄", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case MediaUtil.CHOOSE_PHOTO:
                    if (resultCode == RESULT_OK&&data!=null) {
                        MediaUtil.handleImage(this,data);//处理返回intent中的image 以得到相片的绝对路径imagePath
                        File ImageFile = MediaUtil.getInstance().getImage();
                        Glide.with(this).load(ImageFile)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存到disk硬盘中
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30))
                                        .override(300, 300))
                                .into(binding.imageView);
                    } else {
                        Toast.makeText(ImageEditingActivity.this, "未选择图片", Toast.LENGTH_SHORT).show();
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
        dialog.show();
    }

    /**
     * 监测数据变化
     */
    private void observerDataStateUpdateAction() {

    }

    /**
     * 初始化DataBinding与ViewModel
     */
    private void initActivity() {
        ActivityKt.setWindow(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_editing);
        vm = new ViewModelProvider(this).get(ImageEditingViewModel.class);
    }
}