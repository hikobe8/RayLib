// IMyAidlInterface.aidl
package com.ray.retrofitnetwork;
import com.ray.retrofitnetwork.IRemoteServiceCallback;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerResultCallback(IRemoteServiceCallback callback);
    void add(int a, int b);

}