# AndroidTweaks
不需要重新编译，就可以修改view的参数，保存你想保存的值。

## How to use

>因为他还不够稳定，所以我没有上传到bintray


### App

```
TweakManager.with(this).setPersistent(true).setFloatWindow(true).setShakeEnable(true).init()
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

    val longPressToSaveIcon = Tweak("UI", "LongPress", "save icon", TweakBool(false))

    //这里必须加上上面定义的Tweaks, 比如: longPressToSaveIcon, xxxTweaks, ...
    override val tweakStore = TweakStore().listOf(
            longPressToSaveIcon
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
DebugLibrary.longPressToSaveIcon.boolValue \ floatValue \ stringValue

//动态添加的tweaks
DebugLibrary.value(key)
```
### 启动Tweak程序
```
btn.setOnClickListener {
    TweakManager.start(DebugLibrary)
}
```

## Issue or Pull requests
欢迎大家一起和我来完善这个库

email: charlie.captain@foxmail.com

## 感谢
https://github.com/Khan/SwiftTweaks
