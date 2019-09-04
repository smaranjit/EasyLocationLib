# EasyLocationLib
simple location library

## current version - 0.1.4

### supported android version 0.4+

## Adding to your project

`implementation 'io.github.smaranjit:easylocationlib:0.1.4'`

## Location

### build settings (this will also shows turn on gps dialog, if not already done)

#### for simple uses put this code in main activity, and you are good to go. All settings will be initialized with their default values.

```java
EasyLocationSettings.withActivity(activity).build(); 
```
#### for customize settings

```java
EasyLocationSettings.withActivity(activity)
                .setInterval(2000) //in mili second, default 2000
                .setFastestInterval(1000) //in mili second, default 1000
                .setPriority (EasyLocationSettings.PRIORITY_HIGH_ACCURACY) //default PRIORITY_HIGH_ACCURACY
                .setSmallestDisplacement(0) //in meter, default 0
                .build(); 
```
values for `setPriority` could be

* `EasyLocationSettings.PRIORITY_HIGH_ACCURACY` //maximum power
* `EasyLocationSettings.PRIORITY_BALANCED_POWER_ACCURACY` //balanced power
* `EasyLocationSettings.PRIORITY_LOW_POWER` //minimal power
* `EasyLocationSettings.PRIORITY_NO_POWER` //no power

### starting

`EasyLocation.with(context).listener(listener).start();`

here, `.listener(listener)` is optional, you can add listener later using

`EasyLocation.addListener(listener);`

you can add any no of listener using `addListener`, which implements `EasyLocation.OnLocationEventListener` interface, from any where in the application.
#### to remove a listener call

`EasyLocation.removeListener(listener);`

### stopping

`EasyLocation.with(context).stop();`
