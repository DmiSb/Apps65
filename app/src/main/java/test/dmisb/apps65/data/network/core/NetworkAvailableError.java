package test.dmisb.apps65.data.network.core;

class NetworkAvailableError extends Throwable {

    NetworkAvailableError(){
        super("Network is not available");
    }
}
