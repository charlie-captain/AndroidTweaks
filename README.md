# AndroidTweaks
Tweak your Android app without compiling


## How to use

>It's temporarily unstable, so I won't upload to the bintray

- **Extends TweakLibrary**
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

    following is dynamic loading tweaks

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
- **bind view**
    ```
    ExampleTweakLibrary.bind(ExampleTweakLibrary.switchButton1) {
            btn_example.visibility = if (it as Boolean) View.VISIBLE else View.INVISIBLE
            Log.d("visibility", it.toString())
        }

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
- **start Tweak**
    ```
    TweakManager.with(this).initLibrary(ExampleTweakLibrary)

        btn.setOnClickListener {
            TweakManager.start()
        }
    ```

## Issue or Pull requests
welcome to work with me to make this library better!

email: charlie.captain@foxmail.com

## Thanks
https://github.com/Khan/SwiftTweaks
