# GamepadTest

This Android App listens for connected GameControllers and displays the joysticks,
buttons, and Dpad visually.

Open source, git repo link:  https://github.com/jimandreas/GamepadTest

The app was developed and tested so far with the TRUST GXT 590 Gamepad:

<img src="docs/img/Gamepad-TRUST-GXT-590.PNG" width = 200>

https://www.trust.com/en/product/22258-gxt-590-bosi-bluetooth-wireless-gamepad

## Issues

### Compatibility

This compatibility matrix writeup is the first sign that I have seen
that Google is paying attention to gamepad issues.

That is a good sign.  Unfortunately for Android - looks like it is in Android 10
that things have changed.  And I have Android 9 on my Sony Xperia phone, LOL.   I will
update after I get my hands on an Android 10 based phone.

https://support.google.com/stadia/answer/9578631?hl=en

### Connection as "Input Device" is flakey

What I have found from casual experimentation is that the TRUST GXT 590 toggles between
connecting via BlueTooth to my Xperia phone as a regular device and as an "Input Device".
It MUST be in the "Input Device" mode to work as a GamePad.  So I toggle it by reconnecting.
Sometimes I have to make the Bluetooth system forget the device and do another Pair operation
before things get back to "toggle" mode.   Basically pretty bad for reliability.

## Keymapping requires root access on Android devices

There appear to be problems with the mapping of buttons on Gamepad devices to
Android functions.   For example on the TRUST GXT 590 the "i" button maps annoyingly to
evidently the "HOME" key on Android.  The "HOME" button cannot be controlled by an
app for security reasons.  So the app is stuck and can't do anything about it.  Likewise
the "Back" button is mapped to "Back" on Android.  But it really should be just another
keycode.

Here is a youtube vid that shows the issues - but neglects to mention that you need a rooted
phone to hack your keymaps.   Seems like there is some solution needed for updating keymaps for
gamepads, but the Android frameworks team isn't really up to solving this as yet.

https://www.youtube.com/watch?v=9kCQObr1BM4

What we are left with is a rather unsatisfactory situation with gamepad support.  Reviewing
youtube vids suggests things really haven't changed that much in terms of Gamepad support on Android
since 2012!! (as of this writing it is 2020 - 8 YEARS LATER).

https://www.youtube.com/watch?v=lrZ-xnzYVLA

### Android IssueTracker

37115804 Add support for Xbox One S controller over Bluetooth
https://issuetracker.google.com/issues/37115804

[Android 11 DP/Beta] Bluetooth Gamepads Aren't Reported as Source of Input Events
https://issuetracker.google.com/issues/163120692

[Android 11 Beta] XBox Controller (FLAGGED as WONT FIX)
https://issuetracker.google.com/issues/158758782

### Keymapping issues

On hacking the keymap to workaround the default button mapping:

https://www.reddit.com/r/GeForceNOW/comments/fkw2il/guide_fix_for_the_incorrect_xbox_controller/

### Related apps


#### Unity Android Controller Logger

Evangelos Oganesov

https://play.google.com/store/apps/details?id=com.Evag.UACL

### Gamepad tester

elron

https://play.google.com/store/apps/details?id=ru.elron.gamepadtester&hl=en_US



### See also

https://forum.unity.com/threads/heres-all-the-buttons-for-a-gamepad-for-android.732698/
