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
import java.util.List;

import kotlin.collections.CollectionsKt;

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
            //????????????
            vm.imageFile.setValue(imageCacheFile);
        } else {
            //?????????,???????????????????????????
            if (imageCacheFile.exists()) {
                imageCacheFile.delete();
                imageCacheFile = null;
            }
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
            finish();
        }
    });

    private final ActivityResultLauncher<String> albumLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            File ImageFile = UriKt.copyToCacheFile(uri, this);
            vm.imageFile.setValue(ImageFile);
        } else {
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
            finish();
        }
    });

    //    ????????????
    private final ActivityResultLauncher<String> getMultipleImagesLauncher = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), uris -> {
        if (!uris.isEmpty()) {
            List<File> files = CollectionsKt.map(uris, uri -> UriKt.copyToCacheFile(uri, this));
        } else {
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
            finish();
        }
    });

    private void takePhoto(Context context) {
        if (imageCacheFile == null || !imageCacheFile.exists()) {
            imageCacheFile = new File(context.getCacheDir(), "image/takePicture.jpg");
            FileKt.createFile(imageCacheFile);
        }
        Uri imageUri = FileProvider.getUriForFile(context, "com.eazywrite.app.fileprovider", imageCacheFile);//???????????????????????????????????????????????????Uri??????
        takePhotoLauncher.launch(imageUri);
    }

    /**
     * ?????????????????????
     */
    private void initView() {
        //???????????????????????????????????????
        AlertDialog.Builder dialog = new AlertDialog.Builder(ImageEditingActivity.this);
        dialog.setTitle("????????????");
        dialog.setMessage("?????????????????????");
        dialog.setCancelable(false);
        dialog.setPositiveButton("??????", (dialogInterface, i) -> {
            PermissionX.init(this)
                    .permissions(Manifest.permission.CAMERA)
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            takePhoto(this);
                        } else {
                            Toast.makeText(ImageEditingActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dialog.setNegativeButton("???????????????", (dialogInterface, i) -> {
            albumLauncher.launch("image/*");
        });
        dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0)
                finish();
            return false;
        });
        dialog.show();
        progressDialog = new ProgressDialog(ImageEditingActivity.this);
        progressDialog.setCancelable(false);//????????????false???????????????Back?????????

    }

    /**
     * ??????????????????
     */
    private void observerDataStateUpdateAction() {
        vm.imageFile.observe(this, file -> {
            progressDialog.dismiss();
            Glide.with(ImageEditingActivity.this).load(file)
                    .skipMemoryCache(true)//??????????????????
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//????????????disk?????????
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
            stringBuilder.append("??????????????? ").append(ticksList.size()).append(" ?????????\n");
            /**???????????????????????????**/
            //List<BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO> tickItemList=ticksList.get(0).getItemList();
            for (BillsCropResponse.ResultDTO.ObjectListDTO objectListDTO : ticksList) {
                stringBuilder.append("===???????????????").append(objectListDTO.getTypeDescription()).append("===\n");
                for (BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO itemListDTO : objectListDTO.getItemList()) {
                    stringBuilder.append(itemListDTO.getDescription()).append(" : ").append(itemListDTO.getValue());
                }
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(ImageEditingActivity.this);
            dialog.setTitle("????????????");
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
     * ?????????DataBinding???ViewModel
     */
    private void initActivity() {
        ActivityKt.setWindow(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_editing);
        vm = new ViewModelProvider(this).get(ImageEditingViewModel.class);
        binding.setClick(new Click());
        binding.setLifecycleOwner(this);
    }
}