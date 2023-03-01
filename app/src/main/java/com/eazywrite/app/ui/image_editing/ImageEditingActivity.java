package com.eazywrite.app.ui.image_editing;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eazywrite.app.R;
import com.eazywrite.app.data.model.BillsCropResponse;
import com.eazywrite.app.databinding.ActivityImageEditingBinding;
import com.eazywrite.app.util.ActivityKt;
import com.eazywrite.app.util.FileKt;
import com.eazywrite.app.util.UriKt;
import com.permissionx.guolindev.PermissionX;

import java.io.File;

public class ImageEditingActivity extends AppCompatActivity {

    private ActivityImageEditingBinding binding;
    private ImageEditingViewModel vm;
    private ProgressDialog progressDialog;

    @Nullable
    private File imageCacheFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            observerDataStateUpdateAction();
        }
    }

    public class Click {
        public void editImage(View view) {
            progressDialog.show();
            vm.dewarpImage();
        }

        public void getBills(View view) {
            progressDialog.show();
            vm.getBills();
        }
    }

    private final ActivityResultLauncher<Uri> takePhotoLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), ok -> {
        if (ok) {
            //成功拍摄
            vm.imageFile.setValue(imageCacheFile);
        } else {
            //未拍摄,删除提前创建的文件
            if (imageCacheFile.exists()) {
                imageCacheFile.delete();
                imageCacheFile = null;
            }
            Toast.makeText(this, "未进行拍摄", Toast.LENGTH_SHORT).show();
            finish();
        }
    });

    private final ActivityResultLauncher<String> albumLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            File ImageFile = UriKt.copyToCacheFile(uri, this);
            vm.imageFile.setValue(ImageFile);
        } else {
            Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
            finish();
        }
    });

    private void takePhoto(Context context){
        if (imageCacheFile == null || !imageCacheFile.exists()){
            imageCacheFile = new File(context.getCacheDir(), "image/takePicture.jpg");
            FileKt.createFile(imageCacheFile);
        }
        Uri imageUri = FileProvider.getUriForFile(context, "com.eazywrite.app.fileprovider", imageCacheFile);//通过内容提供器得到刚创建临时文件的Uri对象
        takePhotoLauncher.launch(imageUri);
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
        dialog.setPositiveButton("拍照", (dialogInterface, i) -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.CAMERA)
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted){
                            takePhoto(this);
                        }else {
                            Toast.makeText(ImageEditingActivity.this, "您拒绝了拍照权限", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dialog.setNegativeButton("从相册选择", (dialogInterface, i) -> {
            albumLauncher.launch("image/*");
        });
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                finish();
            return false;
        });
        dialog.show();
        progressDialog = new ProgressDialog(ImageEditingActivity.this);
        progressDialog.setCancelable(false);//如果传入false则不能通过Back键取消

    }

    /**
     * 监测数据变化
     */
    private void observerDataStateUpdateAction() {
        vm.imageFile.observe(this, file -> {
            progressDialog.dismiss();
            Glide.with(ImageEditingActivity.this).load(file)
                    .skipMemoryCache(true)//不做内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存到disk硬盘中
                    .override(2000, 2000)
                    .into(binding.imageView);
        });
        vm.editedImage.observe(this, file -> {
            progressDialog.show();
            vm.cropEnhanceImage();
        });
        vm.tickList.observe(this, ticksList -> {
            progressDialog.dismiss();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("一共识别出 ").append(ticksList.size()).append(" 张票据\n");
            /**每张票据的详细内容**/
            //List<BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO> tickItemList=ticksList.get(0).getItemList();
            for (BillsCropResponse.ResultDTO.ObjectListDTO objectListDTO : ticksList) {
                stringBuilder.append("===这是一张：").append(objectListDTO.getTypeDescription()).append("===\n");
                for (BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO itemListDTO : objectListDTO.getItemList()) {
                    stringBuilder.append(itemListDTO.getDescription()).append(" : ").append(itemListDTO.getValue());
                }
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(ImageEditingActivity.this);
            dialog.setTitle("识别结果");
            dialog.setMessage(stringBuilder.toString());
            dialog.setCancelable(false);
            dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                    finish();
                return false;
            });
            dialog.show();
        });
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