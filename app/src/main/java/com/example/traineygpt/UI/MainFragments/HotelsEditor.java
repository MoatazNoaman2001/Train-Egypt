package com.example.traineygpt.UI.MainFragments;

import com.example.traineygpt.Model.Hotel;
import com.example.traineygpt.Model.Hotel_services;

import java.util.UUID;

public class HotelsEditor {

    public static Hotel[] getAllHotels(){
        Hotel hotel1 = new Hotel(
                UUID.randomUUID().toString().substring(0, 15),
                "Sofitel Legend Old Cataract Aswan",
                "الفنادق غرب مطعم البيت النوبي , مدينة ناصر, Naj‘ al Maḩaţţah 81511",
                "Aswan",
                "+20 102 222 9071",
                "Hotel Complex encompassing famous Victorian-style Cataract Hotel & adjacent high-rise New Cataract Hotel (1963)",
                4.8
        ), hotel2 = new Hotel(
                UUID.randomUUID().toString().substring(0, 15),
                "Mövenpick Aswan",
                "Elephantine Island, Aswan, ASN",
                "Aswan",
                "+20 97 2454455",
                "The hotel is situated on the Elephantine Island on the Nile River\n" +
                        "Offers quiet tropical gardens and terraces\n" +
                        "Spacious rooms, suites and villas that you can choose from based on your own requirements\n" +
                        "Wide range of variety of different restaurants and bars\n" +
                        "Close to tourist attractions",
                4.5
        ), hotel3 = new Hotel(
                UUID.randomUUID().toString().substring(0, 15),
                "Basma Executive Club",
                "Elfanadek Street, Aswan, Aswan Governorate 81111",
                "Aswan",
                "+20972484000",
                "Basma Hotel is located on the highest point of Aswan next to the famous pharaonic monuments and overlooking the beautiful greenery and the amazing Nile Views. \n" +
                        "\n" +
                        "From the moment you step in, you are surrounded by authentic unique art. Hand-painted walls and ceilings immediately give you a head start on the beauty you will enjoy during your stay. Every painted wall, picture, and sculpture you see at Basma Hotel is the work of two of Egypt’s most talented artists; Ayda Ayoub and Yosry Hassan.",
                3.5
        ), hotel4 = new Hotel(
                UUID.randomUUID().toString().substring(0, 15),
                "Sonesta St. George Hotel Luxor",
                "Corniche El Nile Street, Luxor",
                "Luxor",
                "+201283555505",
                "Five-star grandeur on the banks of the Nile awaits at the Sonesta St. George Hotel Luxor. In a city of ancient Pharaonic treasures, this 322-room Luxor, Egypt hotel redefines luxury in a contemporary style. Only minutes from the world-renowned historic sites, the hotel is an ideal setting. After touring, guests relax in the hotel's Key of Life Health Club, dine in one of its restaurants or soak in the pool as they overlook the Nile. Free Wifi in the lobby . Also a happy hour every evening at Nobles Bar with a 15% discount from 7.00 pm to 8.00 pm",
                4.5
        ), hotel5 = new Hotel(
                UUID.randomUUID().toString().substring(0 , 15),
                "Nefertiti Hotel",
                "El Sahabi Street, Via Luxor Temple St, Luxor 85951",
                "Luxor",
                "+20952256086",
                "Welcome to the Nefertiti Hotel - your home away from home in Luxor! One of the first family owned, small hotels located in the historical city of Luxor, the Nefertiti Hotel is well known for its superior customer service, cleanliness and value for money. The hotel was fully refurbished in 2010 and all rooms are equipped with private bathroom, air conditioning, satellite TV, telephone and mini fridge. Daily housekeeping services are provided as well as a complimentary buffet breakfast. Meet people from around the world while lazying on our roof terrace with spectacular views of the River Nile.",
                4.5
        ), hotel6 = new Hotel(
                UUID.randomUUID().toString().substring(0 , 15),
                "InterContinental Cairo Citystars",
                "Omar Ibn El Khattab Street Heliopolis, Cairo 11511 Egypt",
                "Cairo",
                "+02 24619494",
                "Voted Cairo's Best Business Hotel, InterContinental Cairo Citystars offers luxury amid lush gardens in the buzzing hub of Citystars Heliopolis, next to Stars Centre Mall. Whether you are in Cairo for business or leisure, the hotel puts everything at your fingertips, from steak to sushi, from spa treatments to squash, and from complimentary Wi-Fi to cocktails on the air-conditioned poolside terrace. Eleven restaurants and bars are available in the hotel including Shogun Japanese restaurant, Al Khal Egyptian",
                4.5
        ), hotel7 = new Hotel(
                UUID.randomUUID().toString().substring(0 , 15),
                "Kempinski Nile Hotel",
                "12 Ahmed Ragheb Street Cornish El-Nile, Garden City, Cairo, Cairo Governorate 11519",
                "Cairo",
                "+020227980000",
                "Located in Cairo’s affluent Garden City district, Kempinski Nile Hotel offers luxurious rooms on the shores of the Nile River. It features a wellness centre and a rooftop swimming pool. Each of Kempinski’s rooms offer free WiFi, high definition LCD TVs with satellite channels and free soft drinks from the mini bar. For guests’ comfort, we offer the butler service, concierge and pillow menu. Kempinski’s full-service spa and wellness centre offers complimentary hot tubs, steam rooms, and saunas. Guests can also enjoy professional massages, or cool off in the outdoor swimming pool on the hotel’s rooftop.",
                4.5
        );

        hotel1.setSupplies(new String[]{
                Hotel_services.Accessible.name(),
                Hotel_services.Family_friendly.name(),
                Hotel_services.Spa.name(),
                Hotel_services.Swimming_pool.name().replace('_', ' '),
                Hotel_services.Airport_shuttle.name().replace('_', ' '),
                Hotel_services.Hot_tub.name().replace('_', ' '),
                Hotel_services.Fitness_center.name().replace('_', ' '),
                Hotel_services.Business_center.name().replace('_', ' '),
                Hotel_services.Air_conditioned.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Free_parking.name().replace('_', ' '),
                Hotel_services.Free_breakfast.name().replace('_', ' '),
                Hotel_services.Restaurant.name().replace('_', ' ')
        });
        hotel1.setHotelImage(
                new String[]{
                        "https://www.cfmedia.vfmleonardo.com/imageRepo/7/0/111/65/566/1666_su_00_p_3000x2250_P.jpg",
                        "https://www.cfmedia.vfmleonardo.com/imageRepo/5/0/89/131/659/1666_rokga_00_p_3000x2250_P.jpg",
                        "https://www.cfmedia.vfmleonardo.com/imageRepo/5/0/89/131/228/1666_ba_02_p_3000x2250_P.jpg"
                }
        );

        hotel2.setSupplies(new String[]{
                Hotel_services.Free_parking.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Airport_shuttle.name().replace('_', ' '),
                Hotel_services.Accessible.name(),
                Hotel_services.Swimming_pool.name().replace('_', ' '),
                Hotel_services.Family_friendly.name(),
                Hotel_services.Fitness_center.name().replace('_', ' '),
                Hotel_services.Air_conditioned.name().replace('_', ' '),
                Hotel_services.Laundry.name(),
                Hotel_services.Wheelchair_accessible.name().replace('_', ' ')
        });

        hotel2.setHotelImage(new String[]{
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/305316288.jpg?k=a9b7608982328c557408b47262a4a2e92bde0f89af027a6319fa74ea64586f74&o=",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/68678235.jpg?k=18e704216325bcf105f0f6563b44cd301eec32b8a86cedc9806944ce7c27b23e&o=",
                "https://a.mktgcdn.com/p/f0alHKvjBOTa9XP2cmsyqoI6UoksCYduoxkYbdutLDA/1560x1040.jpg"
        });

        hotel3.setSupplies(new String[]{
                Hotel_services.Free_parking.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Car_parking.name().replace('_', ' '),
                Hotel_services.Airport_shuttle.name().replace('_', ' '),
                Hotel_services.Accessible.name(),
                Hotel_services.Swimming_pool.name().replace('_', ' '),

        });
        hotel3.setHotelImage(new String[]{
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/274180247.jpg?k=12e50280db6ff93468046a5e77f6f081f185e57cb48026118d39f7ecfec71d84&o=",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/214132374.jpg?k=b203fa3e5a472f49e219cea358c6d578fd89f645b7db50a64a175ddca8cc07b8&o=",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/279474274.jpg?k=5ac5bd54e5356e9ff97775101a94cae55ef6578bbe60b4c0e304c45eec99347b&o="
        });

        hotel4.setSupplies(new String[]{
                Hotel_services.Free_breakfast.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Patient_Library.name().replace('_', ' '),
                Hotel_services.Accessible.name(),
                Hotel_services.Swimming_pool.name().replace('_', ' '),
                Hotel_services.Spa.name(),
                Hotel_services.Hot_tub.name().replace('_', ' '),
                Hotel_services.Fitness_center.name().replace('_', ' '),
                Hotel_services.Business_center.name().replace('_' , ' '),
                Hotel_services.Restaurant.name(),
                Hotel_services.Air_conditioned.name().replace('_', ' ')
        });
        hotel4.setHotelImage(new String[]{
                "https://cdn.ostrovok.ru/t/1200x616/content/f9/84/f9849e308ea73eb33d3886811437c1d6e842dd9d.jpeg",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/324455927.jpg?k=21b311c035ea2309b4ef9757066ae8dc5582441d1053989e1d7ab85136bb4407&o=",
                "https://images.trvl-media.com/hotels/1000000/70000/69500/69458/ec4a7301_z.jpg"
        });

        hotel5.setSupplies(new String[]{
                Hotel_services.Free_breakfast.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Free_parking.name().replace('_', ' '),
                Hotel_services.Car_parking.name().replace('_', ' '),
                Hotel_services.Airport_shuttle.name().replace('_', ' '),
        });

        hotel5.setHotelImage(new String[]{
                "https://content.r9cdn.net/rimg/himg/dc/70/27/hotelsdotcom-76259152-c2a5465c_w-693241.jpg?crop=true&width=500&height=350",
                "https://content.r9cdn.net/rimg/dimg/ce/93/384292da-city-22396-167759cfe43.jpg",
                "https://tse2.mm.bing.net/th/id/An0qvifar3812pQ480x360?&rs=1&pid=ImgDet"
        });

        hotel6.setSupplies(new String[]{
                Hotel_services.Free_breakfast.name().replace('_', ' '),
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Free_parking.name().replace('_', ' '),
                Hotel_services.Air_conditioned.name().replace('_', ' '),
                Hotel_services.Fitness_center.name().replace('_' ,' '),
                Hotel_services.Free_breakfast.name().replace('_' ,' ')
        });
        hotel6.setHotelImage(new String[]{
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1d/21/35/64/intercontinental-cairo.jpg?w=1200&h=-1&s=1",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1f/c8/8e/e5/al-manial-meeting-room.jpg?w=1200&h=-1&s=1",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/1f/c8/8e/e4/enjoy-the-beautiful-outdoor.jpg?w=1200&h=-1&s=1"
        });

        hotel7.setSupplies(new String[]{
                Hotel_services.Free_WIfi.name().replace('_', ' '),
                Hotel_services.Airport_shuttle.name().replace('_' , ' '),
                Hotel_services.Car_parking.name().replace('_' ,' '),
                Hotel_services.Accessible.name(),
                Hotel_services.Swimming_pool.name().replace('_', ' ')
        });

        hotel7.setHotelImage(new String[]{
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/164457235.jpg?k=3300c80625dbb628d603bd355afd52bf711bde8dfc0cbc5fbf9c8f4d47985f76&o=",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/164455406.jpg?k=06b7871235192f0b288971b1a03d6147fca8fdf643dd946c53985447e24d246a&o=",
                "https://q-xx.bstatic.com/xdata/images/hotel/max1280x900/164454840.jpg?k=4b6b9d6cc4eee5f6cafbb1b43637a052eef0ce9a88d8cdf1412f18b863ae01cb&o="
        });

        Hotel[] hotelsArr = new Hotel[]{
                hotel1 , hotel2 , hotel3, hotel4 , hotel5 , hotel6 , hotel7
        };

        return hotelsArr;
    }
}
