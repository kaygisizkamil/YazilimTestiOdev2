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
![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/3d50f762-fdc2-4c05-9e18-8969d75835e0)
![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/aba967aa-e08c-4ad5-9781-6525102dc899)
![image](https://github.com/kaygisizkamil/YazilimTestiOdev2/assets/96066271/67acbd8f-c29a-4d00-9cd0-6045f4a2ce72)
