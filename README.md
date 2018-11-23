# AndroidTweaks
Tweak your Android app without compiling

[中文文档](https://github.com/charlie-captain/AndroidTweaks/blob/master/Android%20Tweaks%20CN.md)

## How to use

>It's temporarily unstable, so I won't upload to the bintray

### App

```
TweakManager.with(this)
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
            longPressToSaveIcon
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
DebugLibrary.longPressToSaveIcon.value as <the type you want>

//get dynamic tweaks
DebugLibrary.value(key)
```
### start TweakConfig
```
btn.setOnClickListener {
    TweakManager.initLibrary(DebugLibrary).start()
}
```

---

## Issue or Pull requests
welcome to work with me to make this library better!

email: charlie.captain@foxmail.com

## Thanks
https://github.com/Khan/SwiftTweaks
