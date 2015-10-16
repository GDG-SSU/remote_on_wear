# Remote on wear
Projected By. KyeongWan / Ace Kim / Nana Kim

###Project Intro.
Remote on Wear는 가정의 다양한 가전기기들을 매우 심플한 컨트롤 UI의 웨어러블로 컨트롤하기 위한 애플리케이션 입니다. Remote on Wear의 유저는 집안에 에어컨, TV, 셋톱박스, 냉장고 등 어느 가정에나 있을법한 가전들을 소유하고있으며, 스마트폰과 연동되는 Android Wear 기반의 웨어러블 디바이스를 가진 사람들이 사용 가능한 제품입니다.

또한 Remote on Wear는 시각장애인 유저들을 위해 Android wear의 Voice Capabilities를 이용하여 Voice 명령으로도 가전제품을 컨트롤 할 수 있는 애플리케이션으로 기능을 선보입니다.

일단은 시범적으로 LG QRemote와 Android의 ConsumerIRManager를 이용해 LG 가전 단말을 컨트롤 하기위한 제품을 만드는 것을 첫 단추로 계획하고 있습니다.

###Project Structure
![Project Structure](http://s4.postimg.org/6fxb0cmct/2015_10_16_3_12_21.png)

###Use API
####RowClient
- Android Wearable SDK ([UI](https://developer.android.com/intl/ko/training/wearables/ui/index.html) / [Voice Capabilities](https://developer.android.com/intl/ko/training/wearables/apps/voice.html))
- [WIFI Direct](http://developer.android.com/intl/ko/training/connect-devices-wirelessly/wifi-direct.html) ([N:1 연결이 가능합니다](https://www.youtube.com/watch?v=6emgRvH4mTo&list=UUQmz9albYeqArJvmpmaQpGQ&index=1&feature=plcp))

####RowIRSender (리시버와 서비스를 사용합니다. 액티비티를 비롯한 UI요소는 필요하지 않습니다.)
- IR API ([LG QRemote](http://developer.lge.com/resource/mobile/RetrieveDocDevLibrary.dev) / [Android ConsumerIRManager](https://developer.android.com/intl/ko/reference/android/hardware/ConsumerIrManager.html))
- [WIFI Direct](http://developer.android.com/intl/ko/training/connect-devices-wirelessly/wifi-direct.html)

####UI (Wearable Device)
