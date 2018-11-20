# AndroidTweaks
不需要重新编译，就可以修改view的参数，保存你想保存的值。

## How to use

>因为他还不够稳定，所以我没有上传到bintray

- **object 继承 TweakLibrary**

    此处定义静态的Tweaks，现在支持两种类型，int和bool
    ```
    object ExampleTweakLibrary : TweakLibrary() {

        //static
        val switchButton1 = Tweak("UI", "button", "visibility", TweakViewDataType.boolean, true)
        val switchButton2 = Tweak("Test", "category1", "button", TweakViewDataType.boolean, true)
        val switchButton3 = Tweak("Test", "category2", "button", TweakViewDataType.boolean, false)
        val switchButton4 = Tweak("Test", "category3", "button", TweakViewDataType.boolean, true)
        val switchButton5 = Tweak("Test", "category4", "button", TweakViewDataType.boolean, false)
    
    
        override val tweakStore = TweakStore().listOf(
            switchButton1,
            switchButton2,
            switchButton3,
            switchButton4,
            switchButton5
        )
    }
    ```
    
    动态定义Tweak，主要是为了方便设置一个默认值
    
    ```
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        //动态添加,下面举个例子
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "width",
                TweakViewDataType.integer,
                btn.width,
                0,
                screenWidth
            )
        )

        //button高度
        ExampleTweakLibrary.add(
            Tweak(
                "UI",
                "button",
                "height",
                TweakViewDataType.integer,
                btn.height,
                0,
                screenHeight
            )
        )
    ```
- **绑定View**
    ```
    绑定单个Tweak
    ExampleTweakLibrary.bind(ExampleTweakLibrary.switchButton1) {
            btn_example.visibility = if (it as Boolean) View.VISIBLE else View.INVISIBLE
            Log.d("visibility", it.toString())
        }

    //绑定多个Tweaks
    val buttonTweaks = arrayListOf<Tweak>(
        ExampleTweakLibrary.getTweakFromKey("UI_button_width")!!,
        ExampleTweakLibrary.getTweakFromKey("UI_button_height")!!
    )
    //a view bind multiple tweaks
    ExampleTweakLibrary.bindMultiple(buttonTweaks) {
        val width = ExampleTweakLibrary.getTweakValue("UI_button_width")
        val height = ExampleTweakLibrary.getTweakValue("UI_button_height")

        val layout = btn_example.layoutParams
        layout.width = width as Int
        layout.height = height as Int
        btn_example.layoutParams = layout
    }
    ```
- **启动Tweak程序**
    ```
    
    TweakManager.with(this).initLibrary(ExampleTweakLibrary)

    btn.setOnClickListener {
        TweakManager.start()
    }
    ```

## Issue or Pull requests
欢迎大家一起和我来完善这个库

email: charlie.captain@foxmail.com

## 感谢
https://github.com/Khan/SwiftTweaks
