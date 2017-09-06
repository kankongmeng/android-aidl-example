// GetDeviceInfoInterface.aidl
package com.ggstudios.android_aidl_example;

// Declare any non-default types here with import statements

interface GetDeviceInfoInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String getSerialNumber();
    int getVersionCodes();
}
