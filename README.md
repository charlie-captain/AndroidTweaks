# AndroidTweaks
Tweak your Android app without compiling

[![HitCount](http://hits.dwyl.io/charlie-captain/AndroidTweaks.svg)](http://hits.dwyl.io/charlie-captain/AndroidTweaks)

[中文文档](https://github.com/charlie-captain/AndroidTweaks/blob/master/Android%20Tweaks%20CN.md)

## [Release Note](https://github.com/charlie-captain/AndroidTweaks/blob/master/Release%20Note.md)
## How to use

>It's temporarily unstable, so I won't upload to the bintray, so you need to import it into your project

### App

```
TweakManager.with(this).setLibrary(xxxLibrary).setPersistent(true).setFloatWindow(true).setShakeEnable(true).init()
```

### object : TweakLibrary

static tweaks
```
object DebugLibrary : TweakLibrary() {


    /*
     * topic: String
     * category：String
     * title: String
     * type：support TweakBool、 TweakInt、TweakString
     *
     */

    val longPressToSaveIcon = Tweak("UI", "LongPress", "save icon", TweakBool(false))

    //there should add the tweaks into the list, like : longPressToSavePOPIcon, xxxTweaks, ...
    override val tweakStore = TweakStore().listOf(
            longPressToSaveIcon,
            //add your tweak here
    )

}
```

dynamic tweaks

```
    val screenWidth = resources.displayMetrics.widthPixels
    val screenHeight = resources.displayMetrics.heightPixels

    //example
    DebugLibrary.add(
        Tweak(
            "UI",
            "button",
            "width",
            TweakInt(btn_width,0,screenWidth)
        )
    )

    //button_height
    DebugLibrary.add(
        Tweak(
            "UI",
            "button",
            "height",
            TweakInt(btn_height,0,screenHeight)
        )
    )
```

### getValue
```
//static
DebugLibrary.longPressToSaveIcon.value

//get dynamic tweaks
DebugLibrary.value(key,default)
```
### start TweakConfig
```
btn.setOnClickListener {
    TweakManager.start()
}
```

---

## Issue or Pull requests
welcome to work with me to make this library better!

email: charlie.captain@foxmail.com

## Thanks
<https://github.com/Khan/SwiftTweaks>
