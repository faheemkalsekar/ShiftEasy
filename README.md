# ShiftEasy
Use the app to find work (we call them Shifts) at nearby stores and shops. Opportunities to earn are everywhere!. This is a sample app that uses MVP Architecture with Dagger 2. It uses Location services to start and stop a shift. The app caches Shifts Data and Busines Info data. So, this is availabe even in offline mode.

## Introduction
### Functionality
The app is composed of 3 main screen.

#### ShiftsListActivity
1. Allows you to fetch your list of shifts from a URL. Each Shift Item fecthed is kept in the database in Shifts table.
2. Fetches Business Information.
3. Start a shift.
4. Stop a shift.

In offline mode, this Activity will display Shifts List and Business Info. Before starting or stopping a shift, the app checks for location information.

#### ShiftDetailActivity
Displays all information regarding a shift. Also works in offline mode.

#### MapsActivity
Displays Map. Works in offline mode.

## Build Variants
There are 3 build variants.
1. mockDebug : Displays list items from a Fake Respository. Useful for testing purposes.
2. prodDebug : Fetches from a remote server.
3. prodRelease : Fetches from a remote server.

## Building
You can open the project in Android studio and press run. Select prod variant to fetch data from server.

## Testing
There are 2 testing cases. activeShiftDetails_DisplayedInUi & startActivityWithEmptyShiftList. activeShiftDetails_DisplayedInUi tests for Shift details displayed correctly. startActivityWithEmptyShiftList checks if error string is displayed.

### UI Tests
The projects uses Espresso for UI testing.

## Libraries
#### [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
#### [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)
#### [Dagger 2](https://google.github.io/dagger/android) for dependency injection
#### [Retrofit](http://square.github.io/retrofit/) for REST api communication
#### [Picasso](http://square.github.io/picasso/) for image loading
#### [espresso](https://developer.android.com/training/testing/espresso/index.html) for UI tests
#### [mockito](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html) for mocking in tests
#### [easypermissions](https://github.com/googlesamples/easypermissions)
