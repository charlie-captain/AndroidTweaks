# AndroidTweaks
Tweak your Android app without compiling

[中文文档](https://github.com/charlie-captain/AndroidTweaks/blob/master/Android%20Tweaks%20CN.md)

## How to use

>It's temporarily unstable, so I won't upload to the bintray

### App

```
TweakManager.with(this)
```

### object 继承 TweakLibrary

此处定义静态的Tweaks，现在支持两种类型，int和bool
```
object DebugLibrary : TweakLibrary() {


    /*
     * topic: 总分类
     * category：tweak 的所在类别
     * title：tweak 的名称
     * type：现在只支持 TweakBool、 TweakInt、TweakString
     *
     */

    val longPressToSavePOPIcon = Tweak("UI", "LongPress", "save POP icon", TweakBool(false))

    //这里必须加上上面定义的Tweaks, 比如: longPressToSavePOPIcon, xxxTweaks, ...
    override val tweakStore = TweakStore().listOf(
            longPressToSavePOPIcon
    )

}
```

动态定义Tweak，主要是为了方便设置一个默认值

```
    val screenWidth = resources.displayMetrics.widthPixels
    val screenHeight = resources.displayMetrics.heightPixels
    //动态添加,下面举个例子
    DebugLibrary.add(
        Tweak(
            "UI",
            "button",
            "width",
            TweakInt(btn_width,0,screenWidth)
        )
    )

    //button高度
    DebugLibrary.add(
        Tweak(
            "UI",
            "button",
            "height",
            TweakInt(btn_height,0,screenHeight)
        )
    )
```

### 获取值
```
//静态
DebugLibrary.longPressToSavePOPIcon.value as (转换成你想要的类型，一般是不会为null)

//动态添加的tweaks
DebugLibrary.value(key)
```
### 启动Tweak程序
```
btn.setOnClickListener {
    TweakManager..initLibrary(DebugLibrary).start()
}
```

---

## Issue or Pull requests
welcome to work with me to make this library better!

email: charlie.captain@foxmail.com

## Thanks
https://github.com/Khan/SwiftTweaks
