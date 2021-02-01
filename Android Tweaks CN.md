# AndroidTweaks
不需要重新编译，就可以修改view的参数，保存你想保存的值。
> 全kotlin编码，支持AndroidX, Support 

## 如何使用

>因为他还不够稳定，所以我没有上传到bintray，所以你需要将它导入到你的项目里去。


### App

```
TweakManager.with(this).setLibrary(xxxLibrary).setPersistent(true).setFloatWindow(true).setShakeEnable(true).init()
```

### object 继承 TweakLibrary

此处定义静态的Tweak
```
object DebugLibrary : TweakLibrary() {


    /*
     * topic: 总分类
     * category：tweak 的所在类别
     * title：tweak 的名称
     * type：现在只支持 TweakBool、 TweakInt、TweakString
     *
     */

    val longPressToSaveIcon by lazy { Tweak("UI", "LongPress", "save icon", TweakBool(false)) }

}
```

### 获取值
```
//静态
DebugLibrary.longPressToSaveIcon.value
```


### 启动Tweak程序
```
btn.setOnClickListener {
    TweakManager.startActivity()
}
```

## Issue or Pull requests
欢迎大家一起和我来完善这个库

## 感谢
https://github.com/Khan/SwiftTweaks
