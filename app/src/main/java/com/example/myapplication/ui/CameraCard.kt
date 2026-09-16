package com.example.myapplication.ui

import android.Manifest
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.hardware.*
import com.example.myapplication.permissions.rememberPermission
import java.io.File

@Composable
fun CameraCard() {
    val context = LocalContext.current
    val camera = rememberPermission(Manifest.permission.CAMERA)
    val capture = remember { ImageCapture.Builder().build() }
    var photo by remember { mutableStateOf<File?>(null) }

    // TODO 14 variables
    var cam by remember { mutableStateOf<Camera?>(null) }
    var torchOn by remember { mutableStateOf(false) }

    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Field photo", style = MaterialTheme.typography.titleMedium)
            PermissionGate( // GIVEN
                state = camera,
                feature = "Camera",
                reason = "We need the camera to photograph the issue you report."
            ) {
                // TODO 9a / 14: Change call to store camera instance
                CameraPreview(capture, Modifier.fillMaxWidth().height(240.dp)) { cam = it }

                // TODO 9b: Button "Take photo" that calls takePhoto and saves into photo
                Button(onClick = {
                    takePhoto(context, capture) { saved ->
                        photo = saved
                    }
                }) {
                    Text("Take photo")
                }

                // TODO 9c: Text showing the saved file name, only when photo is not null
                photo?.let {
                    Text("Saved: ${it.name}")
                }

                // TODO 13 Shake to take a photo (bonus)
                val shake = rememberAccelerometer()
                var lastShot by remember { mutableLongStateOf(0L) }
                LaunchedEffect(shake) {
                    val now = System.currentTimeMillis()
                    // TODO 13a: if isShake(shake) and more than 1500 ms passed since lastShot:
                    if (isShake(shake) && now - lastShot > 1500) {
                        // TODO 13b: lastShot = now, then takePhoto(...) exactly like the button
                        lastShot = now
                        takePhoto(context, capture) { saved ->
                            photo = saved
                        }
                        // TODO 13c: context.buzz() and Log.d("FieldKit", "Shake capture")
                        context.buzz()
                        Log.d("FieldKit", "Shake capture")
                    }
                }

                // TODO 14a: show the button only if cam?.cameraInfo?.hasFlashUnit() == true
                if (cam?.cameraInfo?.hasFlashUnit() == true) {
                    // TODO 14b: onClick: torchOn = !torchOn, then cam?.cameraControl?.enableTorch(torchOn)
                    Button(onClick = {
                        torchOn = !torchOn
                        cam?.cameraControl?.enableTorch(torchOn)
                    }) {
                        // TODO 14c: button text: "Torch off" when torchOn, else "Torch on"
                        Text(if (torchOn) "Torch off" else "Torch on")
                    }
                }
            }
            // GIVEN (read it, do not change it): thumbnail of the last photo
            photo?.let { f ->
                val thumb = remember(f) { loadThumb(f) }
                thumb?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Last photo",
                        modifier = Modifier.size(96.dp)
                    )
                }
            }
        }
    }
}
