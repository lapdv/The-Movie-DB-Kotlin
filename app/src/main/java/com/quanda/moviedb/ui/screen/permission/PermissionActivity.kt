package com.quanda.moviedb.ui.screen.permission

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.quanda.moviedb.R
import com.quanda.moviedb.data.constants.Constants
import com.quanda.moviedb.databinding.ActivityPermisisonBinding
import com.quanda.moviedb.ui.base.activity.BaseDataLoadActivity
import com.quanda.moviedb.utils.*


class PermissionActivity : BaseDataLoadActivity<ActivityPermisisonBinding, PermissionViewModel>(), PermissionNavigator {

    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS)

    override fun getLayoutId(): Int {
        return R.layout.activity_permisison
    }

    override fun initViewModel(): PermissionViewModel {
        return ViewModelProviders.of(this).get(PermissionViewModel::class.java)
                .apply {
                    navigator = this@PermissionActivity
                }
    }

    override fun initData() {
        super.initData()
        binding.apply {
            view = this@PermissionActivity
            viewModel = this@PermissionActivity.viewModel
        }

        viewModel.apply {
            navigator = this@PermissionActivity
        }
    }

    /**
     * normal way to request runtime permission
     */
    fun case1() {
        if (shouldAskPermission(Manifest.permission.READ_CONTACTS)) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                        .setMessage("Please allow permission to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.READ_CONTACTS),
                                    Constants.REQUEST_READ_CONTACTS)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            } else {
                if (isFirstTimeAskingPermission(Manifest.permission.READ_CONTACTS)) {
                    firstTimeAskingPermission(Manifest.permission.READ_CONTACTS, false)
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_CONTACTS),
                            Constants.REQUEST_READ_CONTACTS)

                    // REQUEST_READ_CONTACTS is an app-defined int constant.
                    // The callback method gets the result of the request.
                } else {
                    //Permission disabled by device policy or user denied permanently. Show proper error message
                    AlertDialog.Builder(this)
                            .setMessage(
                                    "Permission Disabled, Please allow permission to use this feature")
                            .setPositiveButton("OK") { dialog, which ->
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            .setNegativeButton("Cancel") { dialog, which ->

                            }
                            .create().show()
                }
            }
        } else {
            // Permission has already been granted
        }
    }

    /**
     * simple way to request runtime permission with utils
     */
    fun case2() {
        requestPermission(Manifest.permission.READ_CONTACTS, object : PermissionAskListener {
            override fun onNeedPermission() {
                ActivityCompat.requestPermissions(this@PermissionActivity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        Constants.REQUEST_READ_CONTACTS
                )
            }

            override fun onPermissionPreviouslyDenied() {
                AlertDialog.Builder(this@PermissionActivity)
                        .setMessage("Please allow permission to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            ActivityCompat.requestPermissions(this@PermissionActivity,
                                    arrayOf(Manifest.permission.READ_CONTACTS),
                                    Constants.REQUEST_READ_CONTACTS)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            }

            override fun onPermissionDisabled() {
                AlertDialog.Builder(this@PermissionActivity)
                        .setMessage(
                                "Permission Disabled, Please allow permission to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            }

            override fun onPermissionGranted() {
                //   readContacts()
            }
        })
    }

    /**
     * normal way to request multiple runtime permissions
     */
    fun case3() {
        if (shouldAskPermissions(permissions)) {

            // Permissions is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionsRationale(permissions)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request permissions.
                AlertDialog.Builder(this)
                        .setMessage("Please allow permissions to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            ActivityCompat.requestPermissions(this, permissions,
                                    Constants.REQUEST_CAMERA_READ_CONTACTS)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            } else {
                if (isFirstTimeAskingPermissions(permissions)) {
                    firstTimeAskingPermissions(permissions, false)

                    // No explanation needed, we can request permissions.
                    ActivityCompat.requestPermissions(this, permissions,
                            Constants.REQUEST_CAMERA_READ_CONTACTS)

                    // REQUEST_CAMERA_READ_CONTACTS is an app-defined int constant.
                    // The callback method gets the result of the request.
                } else {
                    //Permissions disabled by device policy or user denied permanently. Show proper error message
                    AlertDialog.Builder(this)
                            .setMessage(
                                    "Permissions Disabled, Please allow permissions to use this feature")
                            .setPositiveButton("OK") { dialog, which ->
                                val intent = Intent()
                                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.fromParts("package", packageName, null)
                                intent.data = uri
                                startActivity(intent)
                            }
                            .setNegativeButton("Cancel") { dialog, which ->

                            }
                            .create().show()
                }
            }
        } else {
            // Permission has already been granted
        }
    }

    /**
     * simple way to request multiple runtime permissions with utils
     */
    fun case4() {
        requestPermissions(permissions, object : PermissionAskListener {
            override fun onNeedPermission() {
                ActivityCompat.requestPermissions(this@PermissionActivity, permissions,
                        Constants.REQUEST_CAMERA_READ_CONTACTS)
            }

            override fun onPermissionPreviouslyDenied() {
                AlertDialog.Builder(this@PermissionActivity)
                        .setMessage("Please allow permissions to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            ActivityCompat.requestPermissions(this@PermissionActivity, permissions,
                                    Constants.REQUEST_CAMERA_READ_CONTACTS)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            }

            override fun onPermissionDisabled() {
                AlertDialog.Builder(this@PermissionActivity)
                        .setMessage(
                                "Permissions Disabled, Please allow permissions to use this feature")
                        .setPositiveButton("OK") { dialog, which ->
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton("Cancel") { dialog, which ->

                        }
                        .create().show()
            }

            override fun onPermissionGranted() {
                // permissions granted
            }

        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    Manifest.permission.READ_CONTACTS)) {
                        // permission denied
                        Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        // permission disabled or never ask again
                        Toast.makeText(this, "permission disabled", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            Constants.REQUEST_CAMERA_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size == this@PermissionActivity.permissions.size) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (isPermissionsGranted(grantResults)) {
                        Toast.makeText(this, "permissions granted", Toast.LENGTH_SHORT).show()
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        if (shouldShowRequestPermissionsRationale(
                                        this@PermissionActivity.permissions)) {
                            // permission denied
                            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
                        } else {
                            // permission disabled or never ask again
                            Toast.makeText(this, "permission disabled", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}