# YazilimTestiOdev2
## Proje Yazarı
- Kamil Kaygısız

## Proje İçeriği
- Amazon.com web sitesinin sistem testleri yapildi.
- Testler Docker içinde -headless modda calismaktadir.
- Docker olmadan da main uzerinden calistirabilirsiniz.
- Docker olmadan calistirmak icin main icindeki `System.setProperty` ozelligini yorum satirina alip test classlarindaki headless modlarini silerek interactive olarak calistirabilirsiniz.
- bind failed calismayi engellemez imagein ipv6 yi dinlemeye calismasindan kaynaklanir proje calismasina etkisi yoktur (safe to ignore).

### Projeyi Calistirmak Icin
1. Projeyi clonlayarak veya indirerek proje klasorune `cd` ile yonlendirin.
2. `docker build -t enterthenameyouwant`
3. `docker run -it enterthenameyouwant`

### Ekran Görüntüleri

![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/baca2f20-b462-4b28-a4d4-da10403bd03e)


![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/44018db6-5fd5-4676-9ec5-d5fde4442a56)

![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/03517ea1-d805-41f8-ae0f-0cb4254428e6)
