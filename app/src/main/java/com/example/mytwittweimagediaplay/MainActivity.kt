package com.example.mytwittweimagediaplay

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mytwittweimagediaplay.adapter.ImagePreviewAdapter
import com.example.mytwittweimagediaplay.databinding.ActivityMainBinding
import timber.log.Timber
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val PERMISSION_CODE_WRITE = 10002
    private val PERMISSION_CODE_READ = 10001
    private val REQUEST_CODE = 100
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            if (checkPermissionForImage()) {
                openGalleryForImages()
            }
        }
    }

    private fun checkPermissionForImage(): Boolean {
        var isPermitted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)


                requestPermissions(
                    permission,
                    PERMISSION_CODE_READ
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
                requestPermissions(
                    permissionCoarse,
                    PERMISSION_CODE_WRITE
                ) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
                isPermitted = false
                isPermitted
            } else {
                isPermitted = true
                isPermitted
            }
        }

        return isPermitted
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val imageUriList = arrayListOf<Uri>()
            val count = data.clipData?.itemCount
            //if multiple images are selected this code runs
            if (count != null) {

                for (i in 0 until count) {
                    val imageUri: Uri = data.clipData?.getItemAt(i)?.uri!!
                    imageUriList.add(imageUri)
                }
                setRecyclerView(imageUriList)
            }
            // if single image is selected this code runs
            else if (data.data != null) {

                imageUriList.add(data.data!!)
                setRecyclerView(imageUriList)


            }
        }
    }

    private fun setRecyclerView(imageUriList: ArrayList<Uri>) {
        val adapter = ImagePreviewAdapter()
        adapter.submitList(imageUriList)
        binding.recyclerImagePreview.adapter = adapter
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures"), REQUEST_CODE
            )
        } else { // For latest versions API LEVEL 19+
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE)
        }

    }
}