# Tesla Java API

## What it is
This is a java sdk for tesla api, so you can monitor and control the tesla cars remotely.

The most important question you may focus on:
1. Is Official? No, there has no official api on the world yet. All the api is discoveried by hack the tesla official 
   mobile app.
2. Is Password Safe? **YES**, if you read the source code you will see there is no middle-server to track your 
   information, include email and password. The logic of program is only perform HTTP request to tesla official 
   web interface, like tesla.com, tesla.cn and teslamotors.com, which is used in tesla official mobile app. So feel 
   free to use this java sdk to implement your own cool function instead of worry about the password leak.

## Dependency

Add this lines to your application's pom, and sync pom dependency:
```maven
<dependency>
   <groupId>com.icodeyou</groupId>
   <artifactId>tesla-api</artifactId>
   <version>1.0.0</version>
</dependency>
```

## Usage

Here's a quick example:

```java
public class Main {

   public static void main(String[] args) {
      String accessToken = AuthUtil.login("your email", "your password");

      Long vehicleId = VehicleUtil.getFirstVehicleId(accessToken);
      System.out.println(vehicleId);

      WakeUtil.wakeUp(accessToken, vehicleId);

      ClimateUtil.setTemperature(accessToken, vehicleId, 18);
      ClimateUtil.startAutoConditioning(accessToken, vehicleId);
      ClimateUtil.stopAutoConditioning(accessToken, vehicleId);
      ClimateUtil.setSeatHeater(accessToken, vehicleId, 0, 0);
      ClimateUtil.setWheelHeater(accessToken, vehicleId, false);
      ClimateState climateState = StateUtil.getClimateState(accessToken, vehicleId);
      System.out.println(climateState);

      ChargingUtil.openChargePortDoor(accessToken, vehicleId);
      ChargingUtil.closeChargePortDoor(accessToken, vehicleId);
      ChargingUtil.setChargeLimit(accessToken, vehicleId, 85);

      TrunkUtil.actuateTrunk(accessToken, vehicleId, "rear");
   }

}
```

Enjoy It!