@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.eazywrite.app.ui.image_editing

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.eazywrite.app.util.screenHeight
import com.eazywrite.app.util.screenWidth
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author wilinz
 * @date 2023/3/2 8:34
 */
class CameraXActivity : ComponentActivity() {

    private var isFlashOpen: Boolean by mutableStateOf(false)

    private val albumLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isNotEmpty()) {
                Toast.makeText(this, "正在处理", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                Box(modifier = Modifier.fillMaxSize()) {
                    var previewView by remember {
                        mutableStateOf<PreviewView?>(null)
                    }
                    val scope = rememberCoroutineScope()
                    val cameraPermissionState = rememberPermissionState(
                        android.Manifest.permission.CAMERA,
                        onPermissionResult = { ok ->
                            if (ok) {
                                scope.launch {
                                    while (previewView == null) {
                                        delay(1)
                                    }
                                    startCamera(
                                        previewView!!,
                                        Size(screenWidth, screenHeight)
                                    )
                                }
                            }
                        }
                    )
                    AndroidView(
                        factory = {
                            PreviewView(it).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                )
                                previewView = this
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        update = {
                            setFocusOnClick(it)
                            setAutoFocus(it)
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 64.dp)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f), Alignment.Center
                            ) {
                                IconButton(onClick = {
                                    Toast.makeText(this@CameraXActivity, "长按多选", Toast.LENGTH_SHORT)
                                        .show()
                                    albumLauncher.launch("image/*") }) {
                                    Icon(
                                        imageVector = Icons.Default.PhotoLibrary,
                                        contentDescription = "相册",
                                        tint = Color.White,
                                    )
                                }
                            }
                            Box(modifier = Modifier.weight(1f), Alignment.Center) {
                                CaptureButton {
                                    takePhoto()
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f), Alignment.Center
                            ) {
                                IconButton(onClick = {
                                    camera?.cameraControl?.enableTorch(!isFlashOpen)
                                }) {
                                    Icon(
                                        imageVector = if (isFlashOpen) Icons.Default.FlashlightOn else Icons.Default.FlashlightOff,
                                        contentDescription = "灯光",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }
                    }


                    LaunchedEffect(key1 = Unit, block = {
                        cameraPermissionState.launchPermissionRequest()
                    })
                }
            }
        }
    }

    @Composable
    fun CaptureButton(onClick: () -> Unit) {
        Surface(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .clickable(onClick = onClick),
            color = Color.White
        ) {

            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Capture",
                tint = Color.DarkGray,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }

    private fun setAutoFocus(previewView: PreviewView) {
        previewView.afterMeasured {
            val autoFocusPoint = SurfaceOrientedMeteringPointFactory(1f, 1f)
                .createPoint(.5f, .5f)
            try {
                val autoFocusAction = FocusMeteringAction.Builder(
                    autoFocusPoint,
                    FocusMeteringAction.FLAG_AF
                ).apply {
                    //start auto-focusing after 2 seconds
                    setAutoCancelDuration(2, TimeUnit.SECONDS)
                }.build()
                camera?.cameraControl?.startFocusAndMetering(autoFocusAction)
            } catch (e: CameraInfoUnavailableException) {
                Log.d("ERROR", "cannot access camera", e)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setFocusOnClick(previewView: PreviewView) {
        previewView.setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val factory: MeteringPointFactory = SurfaceOrientedMeteringPointFactory(
                        previewView.width.toFloat(), previewView.height.toFloat()
                    )
                    val autoFocusPoint = factory.createPoint(event.x, event.y)
                    try {
                        camera?.cameraControl?.startFocusAndMetering(
                            FocusMeteringAction.Builder(
                                autoFocusPoint,
                                FocusMeteringAction.FLAG_AF
                            ).apply {
                                //focus only when the user tap the preview
                                disableAutoCancel()
                            }.build()
                        )
                    } catch (e: CameraInfoUnavailableException) {
                        Log.d("ERROR", "cannot access camera", e)
                    }
                    true
                }
                else -> false // Unhandled event.
            }
        }
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            externalCacheDir,
            SimpleDateFormat(
                FILENAME, Locale.CHINA
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }

    private var imageCapture: ImageCapture? = null

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_TYPE = "image/jpeg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    /**
     *  [androidx.camera.core.ImageAnalysis.Builder] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
//        val previewRatio = max(width, height).toDouble() / min(width, height)
//        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
//            return AspectRatio.RATIO_4_3
//        }
        return AspectRatio.RATIO_16_9
    }

    private var camera: Camera? = null

    private fun startCamera(previewView: PreviewView, size: Size) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        val layoutParams = previewView.layoutParams
        Log.d(TAG, "Screen metrics: ${layoutParams.width} x ${layoutParams.height}")

        val screenAspectRatio = aspectRatio(layoutParams.width, layoutParams.height)
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = previewView.display.rotation

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                // We request aspect ratio but no resolution to match preview config, but letting
                // CameraX optimize for whatever specific resolution best fits our use cases
//                .setTargetAspectRatio(screenAspectRatio)
                .setTargetResolution(
                    size
                )
                // Set initial target rotation, we will have to call this again if rotation changes
                // during the lifecycle of this use case
                .setTargetRotation(rotation)
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                val scaleGestureDetector = ScaleGestureDetector(this,
                    object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                        override fun onScale(detector: ScaleGestureDetector): Boolean {
                            val scale =
                                camera!!.cameraInfo.zoomState.value!!.zoomRatio * detector.scaleFactor
                            camera!!.cameraControl.setZoomRatio(scale)
                            return true
                        }
                    })

                previewView.setOnTouchListener { view, event ->
                    view.performClick()
                    scaleGestureDetector.onTouchEvent(event)
                    return@setOnTouchListener true
                }

                camera!!.cameraInfo.torchState.observe(this) {
                    isFlashOpen = it == TorchState.ON
                }
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }


}

inline fun View.afterMeasured(crossinline block: () -> Unit) {
    if (measuredWidth > 0 && measuredHeight > 0) {
        block()
    } else {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    block()
                }
            }
        })
    }
}

const val TAG = "CameraxActivity"
