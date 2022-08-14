package com.mateuszcholyn.wallet.frontend.infrastructure.backup.read

import java.math.BigDecimal

data class BackupSaveModelAll(
    val expenses: List<BackupSaveModel>,
)

data class BackupSaveModel(
    val expenseId: Long,
    val amount: BigDecimal,
    val categoryId: Long,
    val categoryName: String,
    val date: String,
    val description: String,
)


private val prefix = """
    { 
        "expenses":  
""".trimIndent()


private val suffix = """
    }
""".trimIndent()

private val EXPENSES = """
    [ {
      "expenseId" : 711,
      "amount" : 5.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.08.2022 01:36",
      "description" : ""
    }, {
      "expenseId" : 710,
      "amount" : 18.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "14.08.2022 00:58",
      "description" : "zapiekanka "
    }, {
      "expenseId" : 709,
      "amount" : 4.28,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.08.2022 23:56",
      "description" : ""
    }, {
      "expenseId" : 708,
      "amount" : 36.04,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.08.2022 20:27",
      "description" : ""
    }, {
      "expenseId" : 707,
      "amount" : 29.59,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.08.2022 15:27",
      "description" : ""
    }, {
      "expenseId" : 706,
      "amount" : 23.64,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.08.2022 11:20",
      "description" : ""
    }, {
      "expenseId" : 705,
      "amount" : 3.65,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.08.2022 21:41",
      "description" : ""
    }, {
      "expenseId" : 704,
      "amount" : 95.23,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "12.08.2022 21:34",
      "description" : "gaz\n2.96"
    }, {
      "expenseId" : 703,
      "amount" : 9.6,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.08.2022 17:54",
      "description" : "bułki"
    }, {
      "expenseId" : 702,
      "amount" : 19.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.08.2022 22:01",
      "description" : ""
    }, {
      "expenseId" : 701,
      "amount" : 17.97,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.08.2022 17:24",
      "description" : ""
    }, {
      "expenseId" : 700,
      "amount" : 88.75,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "10.08.2022 00:17",
      "description" : "rower rehabilitacyjny dla mamy"
    }, {
      "expenseId" : 699,
      "amount" : 43.04,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.08.2022 21:35",
      "description" : ""
    }, {
      "expenseId" : 698,
      "amount" : 92.88,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "08.08.2022 17:23",
      "description" : "Microservices Patterns"
    }, {
      "expenseId" : 697,
      "amount" : 27.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "08.08.2022 10:45",
      "description" : "kurczak, silver dragon "
    }, {
      "expenseId" : 696,
      "amount" : 41.0,
      "categoryId" : 27,
      "categoryName" : "Pizza",
      "date" : "07.08.2022 22:04",
      "description" : "snap pizza nocna "
    }, {
      "expenseId" : 693,
      "amount" : 56.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.08.2022 22:59",
      "description" : ""
    }, {
      "expenseId" : 692,
      "amount" : 20.94,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.08.2022 20:13",
      "description" : ""
    }, {
      "expenseId" : 691,
      "amount" : 117.96,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "06.08.2022 17:08",
      "description" : "gaz\n3.23"
    }, {
      "expenseId" : 690,
      "amount" : 50.4,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "06.08.2022 00:16",
      "description" : "benzyna\n6.79"
    }, {
      "expenseId" : 689,
      "amount" : 6.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.08.2022 21:45",
      "description" : ""
    }, {
      "expenseId" : 688,
      "amount" : 95.69,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.08.2022 22:03",
      "description" : ""
    }, {
      "expenseId" : 687,
      "amount" : 26.53,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.08.2022 18:36",
      "description" : ""
    }, {
      "expenseId" : 686,
      "amount" : 60.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "01.08.2022 23:12",
      "description" : "Michał Babiarz - Fortepian "
    }, {
      "expenseId" : 685,
      "amount" : 38.46,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.08.2022 21:34",
      "description" : ""
    }, {
      "expenseId" : 684,
      "amount" : 30.6,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "01.08.2022 12:56",
      "description" : "spacerova bistro"
    }, {
      "expenseId" : 683,
      "amount" : 39.87,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "31.07.2022 22:09",
      "description" : ""
    }, {
      "expenseId" : 682,
      "amount" : 24.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "31.07.2022 13:57",
      "description" : ""
    }, {
      "expenseId" : 680,
      "amount" : 17.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "30.07.2022 19:02",
      "description" : "torcik dime + coś co podobno jest smoothie truskawkowym"
    }, {
      "expenseId" : 694,
      "amount" : 27.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.07.2022 15:41",
      "description" : "Internet"
    }, {
      "expenseId" : 695,
      "amount" : 1305.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.07.2022 15:41",
      "description" : "mieszkanie, lipiec 2022"
    }, {
      "expenseId" : 679,
      "amount" : 35.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.07.2022 15:02",
      "description" : ""
    }, {
      "expenseId" : 678,
      "amount" : 15.86,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.07.2022 12:45",
      "description" : ""
    }, {
      "expenseId" : 677,
      "amount" : 4.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "30.07.2022 11:52",
      "description" : "lotto"
    }, {
      "expenseId" : 676,
      "amount" : 122.53,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "30.07.2022 11:52",
      "description" : "gaz\n3.29"
    }, {
      "expenseId" : 674,
      "amount" : 8.6,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.07.2022 12:27",
      "description" : ""
    }, {
      "expenseId" : 675,
      "amount" : 80.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "29.07.2022 12:27",
      "description" : "wymiana filtrów Gazu"
    }, {
      "expenseId" : 673,
      "amount" : 5.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.07.2022 19:13",
      "description" : ""
    }, {
      "expenseId" : 672,
      "amount" : 2.69,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.07.2022 18:35",
      "description" : ""
    }, {
      "expenseId" : 671,
      "amount" : 11.99,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "28.07.2022 18:33",
      "description" : "płyn letni do spryskiwaczy "
    }, {
      "expenseId" : 670,
      "amount" : 5.54,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.07.2022 19:19",
      "description" : ""
    }, {
      "expenseId" : 669,
      "amount" : 21.54,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.07.2022 19:15",
      "description" : ""
    }, {
      "expenseId" : 668,
      "amount" : 60.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "27.07.2022 18:58",
      "description" : "Lekcja pływania numer 13"
    }, {
      "expenseId" : 667,
      "amount" : 137.2,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.07.2022 21:33",
      "description" : "gaz\n3.19"
    }, {
      "expenseId" : 666,
      "amount" : 10.34,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.07.2022 14:40",
      "description" : ""
    }, {
      "expenseId" : 665,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "25.07.2022 21:04",
      "description" : "Michał Babiarz, lekcja"
    }, {
      "expenseId" : 664,
      "amount" : 8.67,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.07.2022 19:29",
      "description" : ""
    }, {
      "expenseId" : 663,
      "amount" : 27.2,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.07.2022 19:27",
      "description" : ""
    }, {
      "expenseId" : 662,
      "amount" : 31.5,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "25.07.2022 15:16",
      "description" : ""
    }, {
      "expenseId" : 661,
      "amount" : 123.9,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "25.07.2022 09:34",
      "description" : "olej do Skody"
    }, {
      "expenseId" : 660,
      "amount" : 17.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.07.2022 08:44",
      "description" : ""
    }, {
      "expenseId" : 659,
      "amount" : 7.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 18:53",
      "description" : "woda"
    }, {
      "expenseId" : 658,
      "amount" : 4.8,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 18:44",
      "description" : "galeria dominikańska, dworzec główny Wrocław "
    }, {
      "expenseId" : 657,
      "amount" : 29.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 15:26",
      "description" : "spiż\npiwo miodowe + Lech limonka mięta"
    }, {
      "expenseId" : 656,
      "amount" : 124.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 14:54",
      "description" : "grecka restauracja "
    }, {
      "expenseId" : 655,
      "amount" : 4.8,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 12:36",
      "description" : "tramwaj"
    }, {
      "expenseId" : 654,
      "amount" : 6.9,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 11:47",
      "description" : "bilety tramwaj, od zoo do dworca"
    }, {
      "expenseId" : 653,
      "amount" : 38.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.07.2022 02:27",
      "description" : "ryba taxi od Piotr do furhouse"
    }, {
      "expenseId" : 652,
      "amount" : 35.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 20:21",
      "description" : "ryba taxi od furhouse do piotrka"
    }, {
      "expenseId" : 651,
      "amount" : 4.8,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 16:46",
      "description" : "bilety tramwaj"
    }, {
      "expenseId" : 650,
      "amount" : 38.33,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 16:28",
      "description" : "pomidory + brykiet 2x"
    }, {
      "expenseId" : 649,
      "amount" : 20.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 16:18",
      "description" : "Marcin Lipski\npokaz ognia"
    }, {
      "expenseId" : 648,
      "amount" : 5.68,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 15:44",
      "description" : ""
    }, {
      "expenseId" : 647,
      "amount" : 17.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 15:21",
      "description" : "mostek południc"
    }, {
      "expenseId" : 646,
      "amount" : 66.0,
      "categoryId" : 17,
      "categoryName" : "Kubek",
      "date" : "23.07.2022 13:18",
      "description" : "32 Kubek Termiczny Panorama Racławicka\n34 Kubek Ala PRL"
    }, {
      "expenseId" : 645,
      "amount" : 85.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 12:14",
      "description" : "panorama Racławicka "
    }, {
      "expenseId" : 644,
      "amount" : 14.97,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 11:43",
      "description" : "drożdżówki "
    }, {
      "expenseId" : 643,
      "amount" : 6.9,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "23.07.2022 11:14",
      "description" : "autobus"
    }, {
      "expenseId" : 642,
      "amount" : 159.58,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.07.2022 23:26",
      "description" : "pociąg"
    }, {
      "expenseId" : 641,
      "amount" : 138.99,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.07.2022 23:17",
      "description" : "żeberka + burger\nsok grejpfrutowy + Balsamico\nBierhalle"
    }, {
      "expenseId" : 511,
      "amount" : 306.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.07.2022 20:54",
      "description" : "fur house Wrocław"
    }, {
      "expenseId" : 640,
      "amount" : 27.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.07.2022 18:13",
      "description" : "herbata mrożona w oczekiwaniu na pociąg"
    }, {
      "expenseId" : 639,
      "amount" : 17.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.07.2022 18:06",
      "description" : ""
    }, {
      "expenseId" : 638,
      "amount" : 2.69,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.07.2022 17:11",
      "description" : ""
    }, {
      "expenseId" : 637,
      "amount" : 5.61,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.07.2022 14:19",
      "description" : ""
    }, {
      "expenseId" : 636,
      "amount" : 16.25,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.07.2022 14:17",
      "description" : ""
    }, {
      "expenseId" : 635,
      "amount" : 8.56,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.07.2022 19:41",
      "description" : ""
    }, {
      "expenseId" : 634,
      "amount" : 28.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "21.07.2022 19:08",
      "description" : "smok dla Skodzinki ❤️"
    }, {
      "expenseId" : 633,
      "amount" : 35.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "21.07.2022 19:07",
      "description" : "smoki dla Zygmunta i Heleny ❤️"
    }, {
      "expenseId" : 632,
      "amount" : 20.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.07.2022 19:31",
      "description" : ""
    }, {
      "expenseId" : 631,
      "amount" : 6.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.07.2022 19:08",
      "description" : ""
    }, {
      "expenseId" : 629,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "20.07.2022 19:06",
      "description" : "Lekcja pływania numer 12"
    }, {
      "expenseId" : 630,
      "amount" : 7.78,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.07.2022 19:06",
      "description" : ""
    }, {
      "expenseId" : 628,
      "amount" : 35.53,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.07.2022 22:37",
      "description" : ""
    }, {
      "expenseId" : 627,
      "amount" : 11.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.07.2022 19:34",
      "description" : ""
    }, {
      "expenseId" : 625,
      "amount" : 47.83,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.07.2022 21:34",
      "description" : ""
    }, {
      "expenseId" : 624,
      "amount" : 10.49,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.07.2022 20:00",
      "description" : ""
    }, {
      "expenseId" : 623,
      "amount" : 22.17,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.07.2022 19:04",
      "description" : ""
    }, {
      "expenseId" : 622,
      "amount" : 52.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "18.07.2022 12:58",
      "description" : "kurczak silver dragon 2x"
    }, {
      "expenseId" : 621,
      "amount" : 49.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "17.07.2022 21:59",
      "description" : "La Luna Italian Pizza\nPizza Nduja"
    }, {
      "expenseId" : 620,
      "amount" : 3.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.07.2022 22:45",
      "description" : ""
    }, {
      "expenseId" : 619,
      "amount" : 16.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.07.2022 17:49",
      "description" : "lody!!"
    }, {
      "expenseId" : 618,
      "amount" : 82.16,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.07.2022 15:02",
      "description" : ""
    }, {
      "expenseId" : 615,
      "amount" : 15.81,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.07.2022 11:20",
      "description" : ""
    }, {
      "expenseId" : 614,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.07.2022 22:00",
      "description" : "doładowanie "
    }, {
      "expenseId" : 613,
      "amount" : 9.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "15.07.2022 16:25",
      "description" : "kask dla Horhe jako prezent Pożegnalny"
    }, {
      "expenseId" : 612,
      "amount" : 129.18,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "13.07.2022 21:42",
      "description" : "gaz\n3.19"
    }, {
      "expenseId" : 611,
      "amount" : 42.72,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.07.2022 19:38",
      "description" : ""
    }, {
      "expenseId" : 610,
      "amount" : 3.59,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.07.2022 19:13",
      "description" : ""
    }, {
      "expenseId" : 609,
      "amount" : 32.99,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "13.07.2022 19:08",
      "description" : "rowatinex"
    }, {
      "expenseId" : 608,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "13.07.2022 18:57",
      "description" : "Lekcja pływania numer 11"
    }, {
      "expenseId" : 607,
      "amount" : 27.85,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.07.2022 19:48",
      "description" : ""
    }, {
      "expenseId" : 605,
      "amount" : 35.42,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.07.2022 21:38",
      "description" : ""
    }, {
      "expenseId" : 606,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "11.07.2022 21:38",
      "description" : "Michał Babiarz - Fortepian "
    }, {
      "expenseId" : 604,
      "amount" : 5.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.07.2022 20:38",
      "description" : ""
    }, {
      "expenseId" : 602,
      "amount" : 36.41,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.07.2022 19:12",
      "description" : ""
    }, {
      "expenseId" : 603,
      "amount" : 6.6,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.07.2022 19:12",
      "description" : ""
    }, {
      "expenseId" : 626,
      "amount" : 120.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "09.07.2022 18:30",
      "description" : "GrotGun dla Dawida z okazji urodzin"
    }, {
      "expenseId" : 601,
      "amount" : 11.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.07.2022 20:44",
      "description" : ""
    }, {
      "expenseId" : 600,
      "amount" : 200.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "08.07.2022 20:17",
      "description" : "wymiana oleju + części + filtry"
    }, {
      "expenseId" : 599,
      "amount" : 17.7,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.07.2022 20:15",
      "description" : ""
    }, {
      "expenseId" : 597,
      "amount" : 19.21,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.07.2022 19:37",
      "description" : ""
    }, {
      "expenseId" : 598,
      "amount" : 111.73,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.07.2022 19:37",
      "description" : ""
    }, {
      "expenseId" : 596,
      "amount" : 101.25,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.07.2022 18:56",
      "description" : "Lekcja pływania numer 10"
    }, {
      "expenseId" : 595,
      "amount" : 23.05,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "07.07.2022 10:14",
      "description" : "Horhe - Prezent Pożegnalny"
    }, {
      "expenseId" : 594,
      "amount" : 10.94,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.07.2022 15:46",
      "description" : ""
    }, {
      "expenseId" : 593,
      "amount" : 162.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "06.07.2022 15:42",
      "description" : "przegląd"
    }, {
      "expenseId" : 592,
      "amount" : 49.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "06.07.2022 11:47",
      "description" : "kurczak w cieście sezamowym\nBar - Orientalny TRE XANH"
    }, {
      "expenseId" : 591,
      "amount" : 10.01,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.07.2022 19:18",
      "description" : ""
    }, {
      "expenseId" : 590,
      "amount" : 26.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "04.07.2022 10:45",
      "description" : ""
    }, {
      "expenseId" : 589,
      "amount" : 10.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.07.2022 22:39",
      "description" : "piwsko"
    }, {
      "expenseId" : 587,
      "amount" : 24.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.07.2022 19:24",
      "description" : "lody!!!"
    }, {
      "expenseId" : 586,
      "amount" : 51.81,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "03.07.2022 16:25",
      "description" : "benzyna\nza iles XD"
    }, {
      "expenseId" : 585,
      "amount" : 18.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "03.07.2022 14:21",
      "description" : "zapachy lawendowe "
    }, {
      "expenseId" : 584,
      "amount" : 20.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.07.2022 14:16",
      "description" : "lemoniada 2x\ndżem malinowy + lawenda"
    }, {
      "expenseId" : 582,
      "amount" : 20.0,
      "categoryId" : 17,
      "categoryName" : "Kubek",
      "date" : "03.07.2022 14:12",
      "description" : "dni lawendy "
    }, {
      "expenseId" : 583,
      "amount" : 13.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "03.07.2022 14:12",
      "description" : "gumka na włosy dla Oli "
    }, {
      "expenseId" : 581,
      "amount" : 18.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "03.07.2022 13:22",
      "description" : "dni lawendy Ostrów"
    }, {
      "expenseId" : 580,
      "amount" : 6.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "03.07.2022 13:09",
      "description" : ""
    }, {
      "expenseId" : 579,
      "amount" : 145.64,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "03.07.2022 12:10",
      "description" : "gaz\n3.45"
    }, {
      "expenseId" : 578,
      "amount" : 43.4,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "02.07.2022 21:34",
      "description" : "pizza!"
    }, {
      "expenseId" : 577,
      "amount" : 25.72,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.07.2022 15:02",
      "description" : ""
    }, {
      "expenseId" : 576,
      "amount" : 28.8,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.07.2022 11:59",
      "description" : ""
    }, {
      "expenseId" : 575,
      "amount" : 33.39,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.07.2022 20:17",
      "description" : ""
    }, {
      "expenseId" : 572,
      "amount" : 54.07,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.07.2022 16:41",
      "description" : ""
    }, {
      "expenseId" : 616,
      "amount" : 1368.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.06.2022 20:39",
      "description" : "mieszkanie - czerwiec"
    }, {
      "expenseId" : 571,
      "amount" : 138.29,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "30.06.2022 20:27",
      "description" : "gaz 3:33"
    }, {
      "expenseId" : 570,
      "amount" : 25.96,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.06.2022 20:17",
      "description" : ""
    }, {
      "expenseId" : 573,
      "amount" : 7.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.06.2022 18:14",
      "description" : ""
    }, {
      "expenseId" : 574,
      "amount" : 30.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.06.2022 18:14",
      "description" : ""
    }, {
      "expenseId" : 569,
      "amount" : 39.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.06.2022 14:29",
      "description" : ""
    }, {
      "expenseId" : 568,
      "amount" : 27.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "29.06.2022 19:03",
      "description" : "burger, numer 10"
    }, {
      "expenseId" : 567,
      "amount" : 22.46,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.06.2022 08:38",
      "description" : ""
    }, {
      "expenseId" : 566,
      "amount" : 14.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.06.2022 22:33",
      "description" : "piwska"
    }, {
      "expenseId" : 565,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "28.06.2022 22:01",
      "description" : "fortepian u Michała Babiarza"
    }, {
      "expenseId" : 564,
      "amount" : 100.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "28.06.2022 17:16",
      "description" : "jak dojadę "
    }, {
      "expenseId" : 563,
      "amount" : 33.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "28.06.2022 13:51",
      "description" : ""
    }, {
      "expenseId" : 562,
      "amount" : 874.16,
      "categoryId" : 24,
      "categoryName" : "Pierdolony Urząd",
      "date" : "27.06.2022 21:57",
      "description" : "jebany zaległy pit kurwa plus pierdolony komornik i odsetki jebane"
    }, {
      "expenseId" : 561,
      "amount" : 76.35,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 20:11",
      "description" : ""
    }, {
      "expenseId" : 560,
      "amount" : 35.1,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 15:14",
      "description" : ""
    }, {
      "expenseId" : 559,
      "amount" : 7.41,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 12:22",
      "description" : ""
    }, {
      "expenseId" : 558,
      "amount" : 16.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 11:55",
      "description" : ""
    }, {
      "expenseId" : 557,
      "amount" : 20.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 11:33",
      "description" : "lody"
    }, {
      "expenseId" : 555,
      "amount" : 16.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "27.06.2022 11:21",
      "description" : "tężnia "
    }, {
      "expenseId" : 556,
      "amount" : 12.08,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.06.2022 11:21",
      "description" : "biedra"
    }, {
      "expenseId" : 554,
      "amount" : 87.59,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.06.2022 14:06",
      "description" : "gaz\n3.39"
    }, {
      "expenseId" : 553,
      "amount" : 5.39,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.06.2022 18:09",
      "description" : ""
    }, {
      "expenseId" : 552,
      "amount" : 13.56,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.06.2022 18:08",
      "description" : ""
    }, {
      "expenseId" : 551,
      "amount" : 3.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 17:45",
      "description" : ""
    }, {
      "expenseId" : 549,
      "amount" : 49.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 17:33",
      "description" : "żeberka "
    }, {
      "expenseId" : 550,
      "amount" : 6.5,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 17:33",
      "description" : "oshee"
    }, {
      "expenseId" : 548,
      "amount" : 20.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 17:31",
      "description" : "kubek"
    }, {
      "expenseId" : 546,
      "amount" : 2.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 16:09",
      "description" : ""
    }, {
      "expenseId" : 547,
      "amount" : 15.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 16:09",
      "description" : "oshee"
    }, {
      "expenseId" : 545,
      "amount" : 8.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 10:28",
      "description" : "góry"
    }, {
      "expenseId" : 544,
      "amount" : 9.12,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "24.06.2022 09:50",
      "description" : ""
    }, {
      "expenseId" : 617,
      "amount" : 95.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "23.06.2022 18:25",
      "description" : "lodówka, serwisant "
    }, {
      "expenseId" : 543,
      "amount" : 3.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.06.2022 17:44",
      "description" : ""
    }, {
      "expenseId" : 542,
      "amount" : 30.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "22.06.2022 12:42",
      "description" : ""
    }, {
      "expenseId" : 541,
      "amount" : 2.59,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.06.2022 08:22",
      "description" : ""
    }, {
      "expenseId" : 540,
      "amount" : 6.06,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.06.2022 22:41",
      "description" : ""
    }, {
      "expenseId" : 539,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "21.06.2022 20:57",
      "description" : "Michał Babiarz - Fortepian "
    }, {
      "expenseId" : 537,
      "amount" : 15.48,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "21.06.2022 18:32",
      "description" : "prezent dla Michała"
    }, {
      "expenseId" : 538,
      "amount" : 7.38,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.06.2022 18:32",
      "description" : ""
    }, {
      "expenseId" : 536,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "21.06.2022 18:09",
      "description" : "Lekcja pływania numer 9"
    }, {
      "expenseId" : 535,
      "amount" : 36.48,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.06.2022 08:50",
      "description" : ""
    }, {
      "expenseId" : 534,
      "amount" : 50.51,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "21.06.2022 01:06",
      "description" : "FactFulness\natomowe nawyki"
    }, {
      "expenseId" : 533,
      "amount" : 28.67,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "20.06.2022 14:47",
      "description" : "Moja Resto Bar\nTagliatelle z Kurczakiem i Brokułami"
    }, {
      "expenseId" : 532,
      "amount" : 26.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.06.2022 19:44",
      "description" : ""
    }, {
      "expenseId" : 531,
      "amount" : 33.76,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.06.2022 21:16",
      "description" : ""
    }, {
      "expenseId" : 530,
      "amount" : 7.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.06.2022 20:41",
      "description" : ""
    }, {
      "expenseId" : 529,
      "amount" : 11.02,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.06.2022 16:46",
      "description" : ""
    }, {
      "expenseId" : 528,
      "amount" : 339.99,
      "categoryId" : 23,
      "categoryName" : "Buty",
      "date" : "18.06.2022 16:17",
      "description" : "buty z Matres Sport"
    }, {
      "expenseId" : 527,
      "amount" : 63.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.06.2022 19:05",
      "description" : ""
    }, {
      "expenseId" : 524,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "16.06.2022 01:56",
      "description" : "doładowanie konta, ja i mama"
    }, {
      "expenseId" : 526,
      "amount" : 40.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "15.06.2022 19:04",
      "description" : "naprawa słuchawek"
    }, {
      "expenseId" : 525,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "15.06.2022 18:59",
      "description" : "Lekcja pływania numer 8"
    }, {
      "expenseId" : 522,
      "amount" : 116.28,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "14.06.2022 22:24",
      "description" : "gaz\n3.44"
    }, {
      "expenseId" : 523,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "14.06.2022 22:24",
      "description" : "fortepian u Michała Babiarza"
    }, {
      "expenseId" : 521,
      "amount" : 12.28,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.06.2022 18:54",
      "description" : ""
    }, {
      "expenseId" : 520,
      "amount" : 1.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.06.2022 18:15",
      "description" : ""
    }, {
      "expenseId" : 519,
      "amount" : 60.08,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.06.2022 21:06",
      "description" : ""
    }, {
      "expenseId" : 518,
      "amount" : 2.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "13.06.2022 14:40",
      "description" : ""
    }, {
      "expenseId" : 517,
      "amount" : 39.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "12.06.2022 15:06",
      "description" : "pizza felicita"
    }, {
      "expenseId" : 516,
      "amount" : 50.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "11.06.2022 23:18",
      "description" : "Malina Trojan"
    }, {
      "expenseId" : 515,
      "amount" : 84.0,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "11.06.2022 15:13",
      "description" : "striptix 2x"
    }, {
      "expenseId" : 514,
      "amount" : 8.47,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.06.2022 15:07",
      "description" : ""
    }, {
      "expenseId" : 513,
      "amount" : 33.99,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "11.06.2022 14:35",
      "description" : "karma dla Balbiny"
    }, {
      "expenseId" : 512,
      "amount" : 25.16,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.06.2022 22:29",
      "description" : ""
    }, {
      "expenseId" : 510,
      "amount" : 50.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "10.06.2022 00:15",
      "description" : "zbiórka na noktowizor dla Ukrainy"
    }, {
      "expenseId" : 509,
      "amount" : 57.35,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.06.2022 22:24",
      "description" : ""
    }, {
      "expenseId" : 508,
      "amount" : 18.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "09.06.2022 13:40",
      "description" : "quesadilla mala"
    }, {
      "expenseId" : 507,
      "amount" : 9.99,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "09.06.2022 08:30",
      "description" : ""
    }, {
      "expenseId" : 505,
      "amount" : 110.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "08.06.2022 23:08",
      "description" : "Szalone Nożyczki\nPrezent Urodzinowy"
    }, {
      "expenseId" : 504,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "08.06.2022 18:12",
      "description" : "Lekcja pływania numer 7"
    }, {
      "expenseId" : 503,
      "amount" : 65.36,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.06.2022 20:27",
      "description" : ""
    }, {
      "expenseId" : 502,
      "amount" : 111.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "07.06.2022 15:56",
      "description" : "chaczapuri"
    }, {
      "expenseId" : 501,
      "amount" : 8.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.06.2022 19:32",
      "description" : ""
    }, {
      "expenseId" : 500,
      "amount" : 16.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "06.06.2022 13:46",
      "description" : "chaczapuri"
    }, {
      "expenseId" : 499,
      "amount" : 17.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.06.2022 18:01",
      "description" : ""
    }, {
      "expenseId" : 498,
      "amount" : 20.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.06.2022 16:12",
      "description" : ""
    }, {
      "expenseId" : 497,
      "amount" : 123.84,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "04.06.2022 23:11",
      "description" : "gaz\n3.49"
    }, {
      "expenseId" : 496,
      "amount" : 50.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.06.2022 21:49",
      "description" : "truskawki + pomidory"
    }, {
      "expenseId" : 495,
      "amount" : 5.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.06.2022 19:52",
      "description" : ""
    }, {
      "expenseId" : 494,
      "amount" : 2.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "03.06.2022 18:34",
      "description" : "parking obok top hifi"
    }, {
      "expenseId" : 492,
      "amount" : 7.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.06.2022 20:51",
      "description" : ""
    }, {
      "expenseId" : 491,
      "amount" : 59.45,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.06.2022 20:39",
      "description" : ""
    }, {
      "expenseId" : 490,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "01.06.2022 18:33",
      "description" : "Lekcja pływania numer 6"
    }, {
      "expenseId" : 489,
      "amount" : 6.87,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.06.2022 18:25",
      "description" : ""
    }, {
      "expenseId" : 506,
      "amount" : 27.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "31.05.2022 23:10",
      "description" : "Internet"
    }, {
      "expenseId" : 493,
      "amount" : 1344.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "31.05.2022 23:08",
      "description" : "MIESZKANIE MAJ 2022"
    }, {
      "expenseId" : 488,
      "amount" : 12.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "31.05.2022 22:42",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 487,
      "amount" : 14.8,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "31.05.2022 22:37",
      "description" : ""
    }, {
      "expenseId" : 486,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "31.05.2022 22:30",
      "description" : "Michał Babiarz - Fortepian"
    }, {
      "expenseId" : 484,
      "amount" : 55.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "30.05.2022 09:28",
      "description" : ""
    }, {
      "expenseId" : 483,
      "amount" : 182.54,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "29.05.2022 14:52",
      "description" : "gaz\n3.65"
    }, {
      "expenseId" : 482,
      "amount" : 4.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.05.2022 02:15",
      "description" : ""
    }, {
      "expenseId" : 481,
      "amount" : 11.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.05.2022 23:16",
      "description" : ""
    }, {
      "expenseId" : 480,
      "amount" : 15.0,
      "categoryId" : 17,
      "categoryName" : "Kubek",
      "date" : "28.05.2022 21:37",
      "description" : "Kubek Skoda 😍"
    }, {
      "expenseId" : 479,
      "amount" : 4.08,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.05.2022 18:00",
      "description" : ""
    }, {
      "expenseId" : 478,
      "amount" : 26.99,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "28.05.2022 17:59",
      "description" : "dla Mateusza Kozłowskiego"
    }, {
      "expenseId" : 476,
      "amount" : 7.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.05.2022 22:39",
      "description" : ""
    }, {
      "expenseId" : 477,
      "amount" : 24.02,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.05.2022 22:39",
      "description" : ""
    }, {
      "expenseId" : 475,
      "amount" : 6.65,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.05.2022 21:56",
      "description" : ""
    }, {
      "expenseId" : 474,
      "amount" : 39.44,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.05.2022 19:00",
      "description" : "zwykle zakupy + kubek! ❤️ Harry Potter"
    }, {
      "expenseId" : 473,
      "amount" : 48.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.05.2022 19:30",
      "description" : ""
    }, {
      "expenseId" : 472,
      "amount" : 66.45,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.05.2022 19:20",
      "description" : ""
    }, {
      "expenseId" : 471,
      "amount" : 12.99,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "25.05.2022 18:53",
      "description" : "koszula do wyczyszczenia"
    }, {
      "expenseId" : 470,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "25.05.2022 18:18",
      "description" : "Lekcja pływania numer 5"
    }, {
      "expenseId" : 469,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "24.05.2022 22:04",
      "description" : "fortepian u Michała Babiarza"
    }, {
      "expenseId" : 468,
      "amount" : 172.94,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "24.05.2022 19:47",
      "description" : "gaz\n3.49"
    }, {
      "expenseId" : 465,
      "amount" : 27.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "23.05.2022 11:24",
      "description" : ""
    }, {
      "expenseId" : 464,
      "amount" : 7.99,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "22.05.2022 21:43",
      "description" : "czekolada dla siostry sąsiadki Anety"
    }, {
      "expenseId" : 462,
      "amount" : 184.62,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "22.05.2022 17:38",
      "description" : "gaz\n3.76"
    }, {
      "expenseId" : 463,
      "amount" : 11.38,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.05.2022 17:38",
      "description" : "woda + chipsy"
    }, {
      "expenseId" : 461,
      "amount" : 68.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.05.2022 12:28",
      "description" : "tarta orzechowa, frytki z nuggetsami, soczki 2x, woda, Obok fontanny we Wrocławiu"
    }, {
      "expenseId" : 460,
      "amount" : 2.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.05.2022 12:23",
      "description" : "wc"
    }, {
      "expenseId" : 459,
      "amount" : 25.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "22.05.2022 10:41",
      "description" : "ogród japoński we Wrocławiu"
    }, {
      "expenseId" : 458,
      "amount" : 87.95,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "21.05.2022 17:01",
      "description" : "dla Piotra Tarnowskiego!"
    }, {
      "expenseId" : 457,
      "amount" : 40.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "21.05.2022 16:29",
      "description" : "kubek taki ręcznie robiony"
    }, {
      "expenseId" : 456,
      "amount" : 25.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "21.05.2022 16:16",
      "description" : "kubek"
    }, {
      "expenseId" : 455,
      "amount" : 10.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "21.05.2022 10:54",
      "description" : "obwarzanki"
    }, {
      "expenseId" : 454,
      "amount" : 112.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "21.05.2022 10:36",
      "description" : "zoo Wrocław\nbilet normalny + bilet studencki"
    }, {
      "expenseId" : 453,
      "amount" : 141.98,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "20.05.2022 23:43",
      "description" : "żeberka + burger\nbierhalle"
    }, {
      "expenseId" : 451,
      "amount" : 320.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "20.05.2022 20:35",
      "description" : "Fur House\nWrocław"
    }, {
      "expenseId" : 452,
      "amount" : 24.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "20.05.2022 20:35",
      "description" : "przejazd autostradą z Krakowa do Wrocławia"
    }, {
      "expenseId" : 450,
      "amount" : 27.79,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.05.2022 11:56",
      "description" : ""
    }, {
      "expenseId" : 449,
      "amount" : 6.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "20.05.2022 00:52",
      "description" : ""
    }, {
      "expenseId" : 448,
      "amount" : 109.2,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "20.05.2022 00:51",
      "description" : "gaz\n3.49"
    }, {
      "expenseId" : 447,
      "amount" : 10.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.05.2022 23:55",
      "description" : "sok ananasowy Irish pub"
    }, {
      "expenseId" : 446,
      "amount" : 14.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "19.05.2022 22:27",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 445,
      "amount" : 44.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.05.2022 20:51",
      "description" : ""
    }, {
      "expenseId" : 444,
      "amount" : 8.95,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.05.2022 22:34",
      "description" : ""
    }, {
      "expenseId" : 443,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "17.05.2022 22:24",
      "description" : "fortepian u Michała Babiarza"
    }, {
      "expenseId" : 441,
      "amount" : 11.85,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.05.2022 21:08",
      "description" : ""
    }, {
      "expenseId" : 442,
      "amount" : 230.0,
      "categoryId" : 22,
      "categoryName" : "Rower",
      "date" : "16.05.2022 21:08",
      "description" : "przegląd + naprawy hamulców, przerzutek"
    }, {
      "expenseId" : 440,
      "amount" : 39.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "16.05.2022 11:00",
      "description" : "Asian Kuchnia Orientalna Dragon"
    }, {
      "expenseId" : 439,
      "amount" : 5.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.05.2022 22:07",
      "description" : "sok"
    }, {
      "expenseId" : 438,
      "amount" : 24.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "15.05.2022 21:47",
      "description" : "tortilla xl z samym mięsem"
    }, {
      "expenseId" : 437,
      "amount" : 100.0,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "15.05.2022 18:23",
      "description" : "gaz\n3.55"
    }, {
      "expenseId" : 466,
      "amount" : 200.0,
      "categoryId" : 21,
      "categoryName" : "Mandaty",
      "date" : "15.05.2022 16:23",
      "description" : "Szczucin, 67 km/h "
    }, {
      "expenseId" : 436,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.05.2022 14:54",
      "description" : "doładowanie mojego telefonu i mamy"
    }, {
      "expenseId" : 435,
      "amount" : 25.34,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.05.2022 08:07",
      "description" : ""
    }, {
      "expenseId" : 434,
      "amount" : 60.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "14.05.2022 07:54",
      "description" : "wymiana opon na letnie"
    }, {
      "expenseId" : 433,
      "amount" : 36.04,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.05.2022 22:18",
      "description" : ""
    }, {
      "expenseId" : 432,
      "amount" : 24.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.05.2022 19:52",
      "description" : ""
    }, {
      "expenseId" : 431,
      "amount" : 3.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "13.05.2022 19:39",
      "description" : ""
    }, {
      "expenseId" : 430,
      "amount" : 130.31,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "13.05.2022 19:35",
      "description" : "gaz\n3.65\n\nbenzyna\n6.99"
    }, {
      "expenseId" : 429,
      "amount" : 6.97,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.05.2022 19:10",
      "description" : ""
    }, {
      "expenseId" : 428,
      "amount" : 50.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "12.05.2022 16:30",
      "description" : "spawanie obok katalizatora"
    }, {
      "expenseId" : 427,
      "amount" : 79.0,
      "categoryId" : 22,
      "categoryName" : "Rower",
      "date" : "12.05.2022 12:08",
      "description" : "wymiana dętki + opony"
    }, {
      "expenseId" : 426,
      "amount" : 16.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.05.2022 11:39",
      "description" : ""
    }, {
      "expenseId" : 425,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "11.05.2022 18:15",
      "description" : "Lekcja pływania numer 4"
    }, {
      "expenseId" : 424,
      "amount" : 4.32,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.05.2022 21:35",
      "description" : ""
    }, {
      "expenseId" : 423,
      "amount" : 53.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "09.05.2022 10:23",
      "description" : "Asiana Kuchnia Orientalna Dragon"
    }, {
      "expenseId" : 422,
      "amount" : 132.05,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "08.05.2022 15:34",
      "description" : "gaz\n3.59"
    }, {
      "expenseId" : 421,
      "amount" : 30.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.05.2022 14:49",
      "description" : "truskawki"
    }, {
      "expenseId" : 418,
      "amount" : 2.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "07.05.2022 00:05",
      "description" : ""
    }, {
      "expenseId" : 417,
      "amount" : 41.44,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.05.2022 21:54",
      "description" : "pomidory suszone dla Oli + koperty na pieniądze na komunię Kamila"
    }, {
      "expenseId" : 416,
      "amount" : 65.98,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "06.05.2022 21:37",
      "description" : "stieprox"
    }, {
      "expenseId" : 415,
      "amount" : 9.05,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.05.2022 21:46",
      "description" : ""
    }, {
      "expenseId" : 414,
      "amount" : 118.37,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "05.05.2022 20:36",
      "description" : "gaz\n3.52"
    }, {
      "expenseId" : 413,
      "amount" : 133.41,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.05.2022 21:15",
      "description" : ""
    }, {
      "expenseId" : 412,
      "amount" : 99.99,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "04.05.2022 20:26",
      "description" : "drugie okulary, bo pierwsze rozjebałem XD"
    }, {
      "expenseId" : 411,
      "amount" : 22.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.05.2022 19:02",
      "description" : "tribiotic dla Balbiny"
    }, {
      "expenseId" : 410,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "04.05.2022 18:56",
      "description" : "Lekcja pływania numer 3"
    }, {
      "expenseId" : 409,
      "amount" : 8.67,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.05.2022 23:35",
      "description" : ""
    }, {
      "expenseId" : 408,
      "amount" : 31.45,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.05.2022 17:36",
      "description" : ""
    }, {
      "expenseId" : 407,
      "amount" : 60.22,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "01.05.2022 19:27",
      "description" : "gaz\n3.69"
    }, {
      "expenseId" : 406,
      "amount" : 50.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "01.05.2022 15:48",
      "description" : "Tata zapomniał zostawić u mamy pieniędze, to ja zostawiałem za niego"
    }, {
      "expenseId" : 419,
      "amount" : 1376.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.04.2022 20:39",
      "description" : "ul. Pychowicka 18G/6"
    }, {
      "expenseId" : 404,
      "amount" : 20.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.04.2022 16:33",
      "description" : ""
    }, {
      "expenseId" : 420,
      "amount" : 23.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.04.2022 14:44",
      "description" : "internet"
    }, {
      "expenseId" : 403,
      "amount" : 32.62,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.04.2022 14:24",
      "description" : ""
    }, {
      "expenseId" : 402,
      "amount" : 3.8,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.04.2022 21:04",
      "description" : ""
    }, {
      "expenseId" : 401,
      "amount" : 13.56,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.04.2022 20:41",
      "description" : ""
    }, {
      "expenseId" : 400,
      "amount" : 83.32,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.04.2022 20:53",
      "description" : ""
    }, {
      "expenseId" : 405,
      "amount" : 711.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "28.04.2022 20:39",
      "description" : "ubezpieczenie"
    }, {
      "expenseId" : 399,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "27.04.2022 21:27",
      "description" : "lekcja pływania numer 2"
    }, {
      "expenseId" : 398,
      "amount" : 70.75,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.04.2022 21:26",
      "description" : ""
    }, {
      "expenseId" : 397,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "26.04.2022 22:04",
      "description" : "Michał Babiarz - Fortepian"
    }, {
      "expenseId" : 396,
      "amount" : 87.98,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "26.04.2022 19:18",
      "description" : "czepek + okulary do pływania"
    }, {
      "expenseId" : 395,
      "amount" : 273.07,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.04.2022 18:39",
      "description" : "gaz 3.35\nbenzyna 6.43"
    }, {
      "expenseId" : 394,
      "amount" : 69.9,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "26.04.2022 11:18",
      "description" : "R2-D2 dla Sebastiana"
    }, {
      "expenseId" : 393,
      "amount" : 53.8,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "25.04.2022 12:31",
      "description" : "Chińczyk"
    }, {
      "expenseId" : 392,
      "amount" : 40.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "24.04.2022 01:11",
      "description" : "pizza xd"
    }, {
      "expenseId" : 391,
      "amount" : 8.3,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "23.04.2022 20:04",
      "description" : ""
    }, {
      "expenseId" : 390,
      "amount" : 78.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "23.04.2022 18:01",
      "description" : ""
    }, {
      "expenseId" : 389,
      "amount" : 108.04,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "23.04.2022 15:00",
      "description" : ""
    }, {
      "expenseId" : 388,
      "amount" : 62.21,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.04.2022 19:46",
      "description" : "zakupy na gofry"
    }, {
      "expenseId" : 387,
      "amount" : 17.53,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.04.2022 21:24",
      "description" : ""
    }, {
      "expenseId" : 386,
      "amount" : 44.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "20.04.2022 20:31",
      "description" : "Jamra, Syrian Food"
    }, {
      "expenseId" : 385,
      "amount" : 100.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "20.04.2022 19:46",
      "description" : "Pierwsza lekcja Pływania!"
    }, {
      "expenseId" : 384,
      "amount" : 12.99,
      "categoryId" : 7,
      "categoryName" : "Ubrania",
      "date" : "20.04.2022 17:37",
      "description" : "wkładki do butów"
    }, {
      "expenseId" : 383,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "19.04.2022 22:19",
      "description" : "Michał Babiarz - Fortepian"
    }, {
      "expenseId" : 382,
      "amount" : 9.48,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.04.2022 17:40",
      "description" : ""
    }, {
      "expenseId" : 381,
      "amount" : 115.88,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "17.04.2022 13:21",
      "description" : "gaz\n3.35"
    }, {
      "expenseId" : 379,
      "amount" : 4.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.04.2022 21:16",
      "description" : ""
    }, {
      "expenseId" : 378,
      "amount" : 17.5,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "16.04.2022 20:48",
      "description" : "zapiekanka hawajska u Endziora"
    }, {
      "expenseId" : 377,
      "amount" : 16.53,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.04.2022 20:58",
      "description" : ""
    }, {
      "expenseId" : 376,
      "amount" : 79.86,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.04.2022 20:54",
      "description" : ""
    }, {
      "expenseId" : 375,
      "amount" : 66.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "15.04.2022 16:04",
      "description" : "burgerki"
    }, {
      "expenseId" : 374,
      "amount" : 11.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "15.04.2022 15:25",
      "description" : "kubek 😍😍😍"
    }, {
      "expenseId" : 373,
      "amount" : 15.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "15.04.2022 15:14",
      "description" : "oscypki i korbacz"
    }, {
      "expenseId" : 380,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.04.2022 13:09",
      "description" : "doładowanie telefonu"
    }, {
      "expenseId" : 372,
      "amount" : 42.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "15.04.2022 12:33",
      "description" : "obiad w schronisku"
    }, {
      "expenseId" : 371,
      "amount" : 108.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "14.04.2022 17:02",
      "description" : "jedzenie"
    }, {
      "expenseId" : 370,
      "amount" : 30.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "14.04.2022 14:32",
      "description" : "parking"
    }, {
      "expenseId" : 369,
      "amount" : 5.222,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.04.2022 09:25",
      "description" : ""
    }, {
      "expenseId" : 368,
      "amount" : 94.07,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "13.04.2022 22:46",
      "description" : "gaz\n3:35"
    }, {
      "expenseId" : 366,
      "amount" : 31.96,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.04.2022 21:26",
      "description" : ""
    }, {
      "expenseId" : 367,
      "amount" : 11.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "13.04.2022 21:26",
      "description" : ""
    }, {
      "expenseId" : 365,
      "amount" : 13.5,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "12.04.2022 14:52",
      "description" : "Mandalorian Pack"
    }, {
      "expenseId" : 364,
      "amount" : 54.08,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.04.2022 19:38",
      "description" : ""
    }, {
      "expenseId" : 363,
      "amount" : 26.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "11.04.2022 14:13",
      "description" : "jakieś jedzenie obok biura XD"
    }, {
      "expenseId" : 362,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "11.04.2022 14:01",
      "description" : "Michał Babiarz\nFortepian"
    }, {
      "expenseId" : 361,
      "amount" : 56.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "10.04.2022 20:37",
      "description" : "Restauracja Gruzińska"
    }, {
      "expenseId" : 360,
      "amount" : 10.0,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "10.04.2022 19:10",
      "description" : "za możliwość gry w good lood"
    }, {
      "expenseId" : 359,
      "amount" : 18.9,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "10.04.2022 17:42",
      "description" : "pierogi z mięsem"
    }, {
      "expenseId" : 358,
      "amount" : 39.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.04.2022 16:20",
      "description" : "maska do włosów Biowax\nargan oil z hebe"
    }, {
      "expenseId" : 357,
      "amount" : 39.99,
      "categoryId" : 7,
      "categoryName" : "Ubrania",
      "date" : "10.04.2022 16:06",
      "description" : "zimowa bluza bawełniana z C & A"
    }, {
      "expenseId" : 356,
      "amount" : 33.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.04.2022 13:21",
      "description" : ""
    }, {
      "expenseId" : 355,
      "amount" : 228.5,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "10.04.2022 11:39",
      "description" : "Lego Skywalker Saga PS5"
    }, {
      "expenseId" : 354,
      "amount" : 77.86,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.04.2022 16:10",
      "description" : ""
    }, {
      "expenseId" : 353,
      "amount" : 28.51,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.04.2022 21:21",
      "description" : ""
    }, {
      "expenseId" : 352,
      "amount" : 5.88,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.04.2022 21:49",
      "description" : ""
    }, {
      "expenseId" : 351,
      "amount" : 110.75,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "06.04.2022 19:07",
      "description" : "gaz\n3.48"
    }, {
      "expenseId" : 350,
      "amount" : 6.91,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.04.2022 17:27",
      "description" : ""
    }, {
      "expenseId" : 349,
      "amount" : 513.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "06.04.2022 13:45",
      "description" : "Naprawy po ostatnim sprawdzeniu samochodu\n+ sprawdzenie drzwi"
    }, {
      "expenseId" : 348,
      "amount" : 70.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "05.04.2022 22:03",
      "description" : "Michał Babiarz - Pianino"
    }, {
      "expenseId" : 346,
      "amount" : 7.37,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.04.2022 17:58",
      "description" : ""
    }, {
      "expenseId" : 345,
      "amount" : 86.97,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "05.04.2022 17:44",
      "description" : "2x rowatinex\n1x witamina d + k"
    }, {
      "expenseId" : 344,
      "amount" : 47.34,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "04.04.2022 10:42",
      "description" : "silver dragon"
    }, {
      "expenseId" : 343,
      "amount" : 92.82,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "03.04.2022 20:11",
      "description" : "maxi pizza"
    }, {
      "expenseId" : 342,
      "amount" : 12.2,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.04.2022 18:47",
      "description" : "piwsko"
    }, {
      "expenseId" : 341,
      "amount" : 152.4,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "02.04.2022 21:34",
      "description" : "gaz 3.59"
    }, {
      "expenseId" : 340,
      "amount" : 19.21,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.04.2022 16:22",
      "description" : ""
    }, {
      "expenseId" : 339,
      "amount" : 44.81,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.04.2022 16:14",
      "description" : ""
    }, {
      "expenseId" : 338,
      "amount" : 19.99,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "01.04.2022 22:48",
      "description" : "Astrofizyka dla Zabieganych"
    }, {
      "expenseId" : 337,
      "amount" : 16.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "01.04.2022 15:59",
      "description" : "hindus food truck obok Allegro"
    }, {
      "expenseId" : 336,
      "amount" : 2.99,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "01.04.2022 08:13",
      "description" : "drożdżówka"
    }, {
      "expenseId" : 335,
      "amount" : 67.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "31.03.2022 21:51",
      "description" : "Joker Pizza!"
    }, {
      "expenseId" : 334,
      "amount" : 24.0,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "31.03.2022 20:05",
      "description" : "domowa lemoniada"
    }, {
      "expenseId" : 332,
      "amount" : 100.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "31.03.2022 20:04",
      "description" : "sprawdzenie skody"
    }, {
      "expenseId" : 333,
      "amount" : 44.0,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "31.03.2022 20:04",
      "description" : "bilard\nstage music club"
    }, {
      "expenseId" : 331,
      "amount" : 45.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "31.03.2022 18:46",
      "description" : "wymiana wycieraczek"
    }, {
      "expenseId" : 347,
      "amount" : 1333.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "31.03.2022 18:35",
      "description" : "mieszkanie, marzec 2022"
    }, {
      "expenseId" : 330,
      "amount" : 22.53,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.03.2022 21:03",
      "description" : ""
    }, {
      "expenseId" : 329,
      "amount" : 15.92,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.03.2022 22:05",
      "description" : ""
    }, {
      "expenseId" : 328,
      "amount" : 83.46,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.03.2022 20:39",
      "description" : ""
    }, {
      "expenseId" : 327,
      "amount" : 44.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "28.03.2022 12:07",
      "description" : "2x kurczak silver dragon"
    }, {
      "expenseId" : 326,
      "amount" : 14.0,
      "categoryId" : 20,
      "categoryName" : "Jedzenie na mieście",
      "date" : "27.03.2022 00:37",
      "description" : "zapiekanka hawajska na Kazimierzu"
    }, {
      "expenseId" : 325,
      "amount" : 6.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "27.03.2022 00:01",
      "description" : ""
    }, {
      "expenseId" : 324,
      "amount" : 121.39,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.03.2022 23:47",
      "description" : "gaz\n3.66"
    }, {
      "expenseId" : 323,
      "amount" : 55.37,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.03.2022 17:09",
      "description" : ""
    }, {
      "expenseId" : 322,
      "amount" : 7.18,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.03.2022 01:47",
      "description" : ""
    }, {
      "expenseId" : 321,
      "amount" : 20.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "25.03.2022 17:47",
      "description" : "napiwek za grę Czardasza"
    }, {
      "expenseId" : 320,
      "amount" : 39.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "25.03.2022 13:39",
      "description" : "Restauracja Chinkalnia"
    }, {
      "expenseId" : 319,
      "amount" : 100.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "25.03.2022 08:08",
      "description" : "jakDojade"
    }, {
      "expenseId" : 318,
      "amount" : 4.49,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.03.2022 08:07",
      "description" : ""
    }, {
      "expenseId" : 317,
      "amount" : 23.59,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.03.2022 21:26",
      "description" : ""
    }, {
      "expenseId" : 316,
      "amount" : 6.52,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.03.2022 21:03",
      "description" : ""
    }, {
      "expenseId" : 315,
      "amount" : 40.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "22.03.2022 21:22",
      "description" : "wycieraczki"
    }, {
      "expenseId" : 314,
      "amount" : 31.72,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.03.2022 18:39",
      "description" : ""
    }, {
      "expenseId" : 313,
      "amount" : 45.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "21.03.2022 11:54",
      "description" : "kurczak w cieście kokosowym"
    }, {
      "expenseId" : 312,
      "amount" : 5.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "20.03.2022 16:24",
      "description" : ""
    }, {
      "expenseId" : 311,
      "amount" : 73.77,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.03.2022 11:24",
      "description" : ""
    }, {
      "expenseId" : 310,
      "amount" : 9.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.03.2022 19:29",
      "description" : ""
    }, {
      "expenseId" : 309,
      "amount" : 161.05,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "18.03.2022 18:23",
      "description" : "gaz\n3.77"
    }, {
      "expenseId" : 308,
      "amount" : 10.24,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.03.2022 22:06",
      "description" : ""
    }, {
      "expenseId" : 307,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "17.03.2022 21:57",
      "description" : ""
    }, {
      "expenseId" : 306,
      "amount" : 70.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.03.2022 21:37",
      "description" : "Muzeum Gier Kraków"
    }, {
      "expenseId" : 305,
      "amount" : 31.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "17.03.2022 14:25",
      "description" : "chaczapuri"
    }, {
      "expenseId" : 304,
      "amount" : 4.61,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.03.2022 08:09",
      "description" : ""
    }, {
      "expenseId" : 303,
      "amount" : 59.26,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "16.03.2022 21:18",
      "description" : "Event Versioning"
    }, {
      "expenseId" : 302,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.03.2022 19:09",
      "description" : "doładowanie"
    }, {
      "expenseId" : 301,
      "amount" : 44.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "14.03.2022 11:31",
      "description" : "kurczak\nsilver dragon"
    }, {
      "expenseId" : 300,
      "amount" : 100.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "13.03.2022 10:45",
      "description" : "remont łazienki mamy z Wykop"
    }, {
      "expenseId" : 299,
      "amount" : 62.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "12.03.2022 21:30",
      "description" : "Cud Malina\nWadowice"
    }, {
      "expenseId" : 298,
      "amount" : 3.61,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.03.2022 01:58",
      "description" : ""
    }, {
      "expenseId" : 297,
      "amount" : 19.03,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.03.2022 20:07",
      "description" : ""
    }, {
      "expenseId" : 296,
      "amount" : 22.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "11.03.2022 12:05",
      "description" : ""
    }, {
      "expenseId" : 295,
      "amount" : 27.24,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.03.2022 20:21",
      "description" : ""
    }, {
      "expenseId" : 294,
      "amount" : 30.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "10.03.2022 15:37",
      "description" : "włoska restauracja obok pracy ta co ma fortepian"
    }, {
      "expenseId" : 293,
      "amount" : 22.09,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.03.2022 20:53",
      "description" : ""
    }, {
      "expenseId" : 292,
      "amount" : 70.0,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "08.03.2022 18:52",
      "description" : "stieprox"
    }, {
      "expenseId" : 291,
      "amount" : 49.79,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.03.2022 18:51",
      "description" : ""
    }, {
      "expenseId" : 290,
      "amount" : 108.16,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "07.03.2022 20:47",
      "description" : "gaz\n3.27"
    }, {
      "expenseId" : 289,
      "amount" : 20.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "07.03.2022 20:38",
      "description" : "dla Oli na dzień kobiet"
    }, {
      "expenseId" : 288,
      "amount" : 66.97,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "07.03.2022 20:32",
      "description" : "czyszczenie garnituru"
    }, {
      "expenseId" : 287,
      "amount" : 35.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "07.03.2022 12:14",
      "description" : "zupa Tom kha Gai\nkurczak pieczony\nz silver dragon"
    }, {
      "expenseId" : 286,
      "amount" : 100.0,
      "categoryId" : 21,
      "categoryName" : "Mandaty",
      "date" : "06.03.2022 23:52",
      "description" : "za brak jebanych świateł jak wracałem z Warszawy z urodzin Oli Rusek"
    }, {
      "expenseId" : 285,
      "amount" : 19.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.03.2022 22:11",
      "description" : "tortilla w efes kebab"
    }, {
      "expenseId" : 284,
      "amount" : 11.37,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "06.03.2022 22:01",
      "description" : ""
    }, {
      "expenseId" : 283,
      "amount" : 109.97,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "05.03.2022 20:25",
      "description" : "dla Nadii i Kamila"
    }, {
      "expenseId" : 282,
      "amount" : 103.7,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.03.2022 11:26",
      "description" : ""
    }, {
      "expenseId" : 280,
      "amount" : 128.6,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "03.03.2022 21:15",
      "description" : "gaz\n2.89\nMoya obok Kaufland"
    }, {
      "expenseId" : 279,
      "amount" : 11.27,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.03.2022 21:04",
      "description" : ""
    }, {
      "expenseId" : 278,
      "amount" : 19.99,
      "categoryId" : 12,
      "categoryName" : "Elektronika",
      "date" : "03.03.2022 19:06",
      "description" : "baterie AAA"
    }, {
      "expenseId" : 277,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "03.03.2022 18:24",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 276,
      "amount" : 16.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "03.03.2022 12:42",
      "description" : "quesadilla"
    }, {
      "expenseId" : 275,
      "amount" : 6.56,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.03.2022 08:53",
      "description" : ""
    }, {
      "expenseId" : 274,
      "amount" : 49.05,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.03.2022 20:51",
      "description" : ""
    }, {
      "expenseId" : 272,
      "amount" : 450.21,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "28.02.2022 20:28",
      "description" : "leki na pomoc Ukrainie"
    }, {
      "expenseId" : 273,
      "amount" : 75.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "28.02.2022 20:28",
      "description" : "pino Garden"
    }, {
      "expenseId" : 281,
      "amount" : 1374.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "28.02.2022 19:02",
      "description" : "Luty 2022"
    }, {
      "expenseId" : 271,
      "amount" : 24.5,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "28.02.2022 11:22",
      "description" : "silver dragon"
    }, {
      "expenseId" : 270,
      "amount" : 106.12,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.02.2022 19:47",
      "description" : ""
    }, {
      "expenseId" : 269,
      "amount" : 24.05,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.02.2022 14:53",
      "description" : ""
    }, {
      "expenseId" : 268,
      "amount" : 1.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.02.2022 14:48",
      "description" : "soczek"
    }, {
      "expenseId" : 267,
      "amount" : 45.07,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.02.2022 14:29",
      "description" : "2.59 gaz"
    }, {
      "expenseId" : 266,
      "amount" : 11.98,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "26.02.2022 14:27",
      "description" : "hot dozki"
    }, {
      "expenseId" : 265,
      "amount" : 100.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "26.02.2022 00:16",
      "description" : "na pomoc dla Ukrainy"
    }, {
      "expenseId" : 264,
      "amount" : 17.39,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "25.02.2022 20:45",
      "description" : ""
    }, {
      "expenseId" : 263,
      "amount" : 114.63,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.02.2022 20:39",
      "description" : ""
    }, {
      "expenseId" : 262,
      "amount" : 185.68,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "24.02.2022 20:48",
      "description" : "gaz 2.75\nbenzyna 5.74"
    }, {
      "expenseId" : 261,
      "amount" : 10.16,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.02.2022 20:08",
      "description" : ""
    }, {
      "expenseId" : 260,
      "amount" : 4.73,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.02.2022 18:03",
      "description" : ""
    }, {
      "expenseId" : 258,
      "amount" : 16.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "24.02.2022 13:09",
      "description" : "quesadilla"
    }, {
      "expenseId" : 259,
      "amount" : 3.32,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.02.2022 08:10",
      "description" : "soczek"
    }, {
      "expenseId" : 257,
      "amount" : 17.5,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "23.02.2022 21:28",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 256,
      "amount" : 33.5,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "23.02.2022 20:29",
      "description" : "rowatinex, 50 kapsułek"
    }, {
      "expenseId" : 255,
      "amount" : 43.42,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.02.2022 19:31",
      "description" : ""
    }, {
      "expenseId" : 254,
      "amount" : 24.5,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "22.02.2022 12:11",
      "description" : ""
    }, {
      "expenseId" : 253,
      "amount" : 10.33,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.02.2022 21:46",
      "description" : ""
    }, {
      "expenseId" : 252,
      "amount" : 121.26,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "21.02.2022 19:42",
      "description" : "gaz\n2.64"
    }, {
      "expenseId" : 251,
      "amount" : 8.26,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.02.2022 18:21",
      "description" : ""
    }, {
      "expenseId" : 250,
      "amount" : 12.52,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.02.2022 17:56",
      "description" : ""
    }, {
      "expenseId" : 249,
      "amount" : 80.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "21.02.2022 12:05",
      "description" : "wymiana filtrów gazu"
    }, {
      "expenseId" : 248,
      "amount" : 27.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "21.02.2022 12:04",
      "description" : "2x\npierogi ruskie"
    }, {
      "expenseId" : 247,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "20.02.2022 16:32",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 246,
      "amount" : 71.01,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "20.02.2022 11:38",
      "description" : "gaz, 2.59"
    }, {
      "expenseId" : 245,
      "amount" : 13.3,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.02.2022 17:27",
      "description" : ""
    }, {
      "expenseId" : 244,
      "amount" : 50.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "19.02.2022 13:33",
      "description" : "skarpetki XD"
    }, {
      "expenseId" : 242,
      "amount" : 4.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.02.2022 20:53",
      "description" : "piwsko"
    }, {
      "expenseId" : 241,
      "amount" : 15.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "18.02.2022 20:37",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 240,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "18.02.2022 19:54",
      "description" : "zapiekanka"
    }, {
      "expenseId" : 239,
      "amount" : 60.0,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "18.02.2022 13:00",
      "description" : "ElRach Kielce\nRozliczenie PIT za 2021"
    }, {
      "expenseId" : 238,
      "amount" : 59.99,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "17.02.2022 20:41",
      "description" : "kocyk dla dziecka Kariny i Sebastiana ❤️"
    }, {
      "expenseId" : 237,
      "amount" : 6.48,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.02.2022 20:30",
      "description" : ""
    }, {
      "expenseId" : 236,
      "amount" : 42.37,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.02.2022 19:08",
      "description" : ""
    }, {
      "expenseId" : 235,
      "amount" : 29.99,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "16.02.2022 20:56",
      "description" : "rowatinex, 50 kapsułek"
    }, {
      "expenseId" : 234,
      "amount" : 52.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "16.02.2022 20:08",
      "description" : "chaczapuri obok kościoła mariackiego"
    }, {
      "expenseId" : 233,
      "amount" : 39.0,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "15.02.2022 20:33",
      "description" : "Kino Cinema City\nŚmierć na Nilu"
    }, {
      "expenseId" : 232,
      "amount" : 67.92,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.02.2022 20:07",
      "description" : ""
    }, {
      "expenseId" : 231,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.02.2022 17:20",
      "description" : "doładowanie dla mnie i mamy"
    }, {
      "expenseId" : 230,
      "amount" : 2.8,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.02.2022 09:34",
      "description" : ""
    }, {
      "expenseId" : 229,
      "amount" : 66.97,
      "categoryId" : 19,
      "categoryName" : "Usługi",
      "date" : "14.02.2022 19:44",
      "description" : "czyszczenie garnituru"
    }, {
      "expenseId" : 226,
      "amount" : 149.23,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.02.2022 19:33",
      "description" : ""
    }, {
      "expenseId" : 227,
      "amount" : 10.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "14.02.2022 19:33",
      "description" : ""
    }, {
      "expenseId" : 228,
      "amount" : 128.45,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "14.02.2022 19:33",
      "description" : "2.59 gaz"
    }, {
      "expenseId" : 225,
      "amount" : 27.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "11.02.2022 13:05",
      "description" : "internet za luty 2022"
    }, {
      "expenseId" : 224,
      "amount" : 38.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "11.02.2022 12:28",
      "description" : "silver dragon"
    }, {
      "expenseId" : 223,
      "amount" : 47.99,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "09.02.2022 13:20",
      "description" : "Tbilisuri Pierogarnia"
    }, {
      "expenseId" : 222,
      "amount" : 60.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "08.02.2022 17:38",
      "description" : "urodziny Jacka Guzika"
    }, {
      "expenseId" : 221,
      "amount" : 13.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "07.02.2022 13:28",
      "description" : "prezent dla Tomka Szewczyka"
    }, {
      "expenseId" : 220,
      "amount" : 53.4,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.02.2022 22:21",
      "description" : "Pizzeria Capri New\nPizza Chicago"
    }, {
      "expenseId" : 219,
      "amount" : 120.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.02.2022 21:32",
      "description" : "zakupy covidowe zrobione przez Szczepana"
    }, {
      "expenseId" : 218,
      "amount" : 46.9,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "03.02.2022 00:43",
      "description" : "Pizzeria Nocna Kraków\nPizza Capricciosa"
    }, {
      "expenseId" : 217,
      "amount" : 169.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "02.02.2022 00:51",
      "description" : "długopis inż. Aleksandra Sołtys"
    }, {
      "expenseId" : 216,
      "amount" : 34.68,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.02.2022 21:54",
      "description" : ""
    }, {
      "expenseId" : 243,
      "amount" : 1435.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "31.01.2022 10:04",
      "description" : "mieszkanie za styczeń"
    }, {
      "expenseId" : 215,
      "amount" : 50.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "30.01.2022 21:03",
      "description" : "WOŚP 30 Edycja"
    }, {
      "expenseId" : 214,
      "amount" : 50.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "30.01.2022 20:19",
      "description" : "na wyciskarke dla Magdy na 30 urodziny"
    }, {
      "expenseId" : 213,
      "amount" : 2.89,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.01.2022 14:44",
      "description" : ""
    }, {
      "expenseId" : 212,
      "amount" : 5.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.01.2022 14:31",
      "description" : "napiwek dla omomomo"
    }, {
      "expenseId" : 211,
      "amount" : 154.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "29.01.2022 13:30",
      "description" : "U Barrssa"
    }, {
      "expenseId" : 210,
      "amount" : 154.65,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "29.01.2022 02:13",
      "description" : "3.14 gaz"
    }, {
      "expenseId" : 209,
      "amount" : 97.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "28.01.2022 15:46",
      "description" : "karczma człekówka"
    }, {
      "expenseId" : 208,
      "amount" : 10.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.01.2022 10:22",
      "description" : "ramka na zdjęcia"
    }, {
      "expenseId" : 207,
      "amount" : 4.95,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "28.01.2022 10:12",
      "description" : "zdjęcia Andrzeja na pogrzeb"
    }, {
      "expenseId" : 206,
      "amount" : 8.47,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.01.2022 18:03",
      "description" : ""
    }, {
      "expenseId" : 205,
      "amount" : 48.99,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "27.01.2022 11:27",
      "description" : ""
    }, {
      "expenseId" : 204,
      "amount" : 118.07,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "26.01.2022 22:17",
      "description" : "gaz 2.99\nbenzyna 5.76\nmoya obok Kaufland"
    }, {
      "expenseId" : 203,
      "amount" : 11.37,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "26.01.2022 20:42",
      "description" : "kobieta obok Kaufland"
    }, {
      "expenseId" : 202,
      "amount" : 29.47,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.01.2022 20:37",
      "description" : ""
    }, {
      "expenseId" : 201,
      "amount" : 47.99,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "26.01.2022 12:37",
      "description" : "Tbilisiri Pierogarnia"
    }, {
      "expenseId" : 200,
      "amount" : 44.99,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "25.01.2022 11:14",
      "description" : "Tbilisuri Pierogarnia"
    }, {
      "expenseId" : 199,
      "amount" : 24.17,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "24.01.2022 11:55",
      "description" : "Chińczyk"
    }, {
      "expenseId" : 198,
      "amount" : 20.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "23.01.2022 19:50",
      "description" : "lemoniada w The Stage"
    }, {
      "expenseId" : 197,
      "amount" : 150.0,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "23.01.2022 18:38",
      "description" : "na paliwo dla T4 jak Szczepan jechał"
    }, {
      "expenseId" : 196,
      "amount" : 118.76,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "23.01.2022 15:28",
      "description" : "gaz, 3.08"
    }, {
      "expenseId" : 195,
      "amount" : 17.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "22.01.2022 22:32",
      "description" : "pizza!"
    }, {
      "expenseId" : 194,
      "amount" : 45.03,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.01.2022 18:37",
      "description" : "na imprezę u Sebastiana"
    }, {
      "expenseId" : 193,
      "amount" : 44.63,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.01.2022 14:46",
      "description" : ""
    }, {
      "expenseId" : 192,
      "amount" : 59.7,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.01.2022 20:49",
      "description" : "kubek za 14,99 z baby yoda wśród zakupów"
    }, {
      "expenseId" : 191,
      "amount" : 25.24,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "20.01.2022 20:48",
      "description" : "czteropak dla Kuby za nocleg \n+ zwykle zakupy"
    }, {
      "expenseId" : 190,
      "amount" : 26.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "20.01.2022 17:41",
      "description" : "czekolada mleczna do picia"
    }, {
      "expenseId" : 189,
      "amount" : 89.78,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "20.01.2022 16:55",
      "description" : "2.97 gaz"
    }, {
      "expenseId" : 188,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "20.01.2022 16:35",
      "description" : "napiwek, A Propos w Ostrowcu świętokrzyskim"
    }, {
      "expenseId" : 187,
      "amount" : 120.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "19.01.2022 23:41",
      "description" : "u Waldiego"
    }, {
      "expenseId" : 185,
      "amount" : 101.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "19.01.2022 19:03",
      "description" : "karczma u Jana obok Starachowic"
    }, {
      "expenseId" : 183,
      "amount" : 85.89,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "19.01.2022 13:26",
      "description" : "3.08 gaz"
    }, {
      "expenseId" : 184,
      "amount" : 10.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "19.01.2022 13:26",
      "description" : ""
    }, {
      "expenseId" : 182,
      "amount" : 5.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.01.2022 00:16",
      "description" : "hot dog 🤩"
    }, {
      "expenseId" : 181,
      "amount" : 10.68,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.01.2022 18:45",
      "description" : "wrapy i parówki"
    }, {
      "expenseId" : 180,
      "amount" : 42.7,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "17.01.2022 17:04",
      "description" : ""
    }, {
      "expenseId" : 179,
      "amount" : 99.99,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "16.01.2022 15:57",
      "description" : "diesel, 5,79"
    }, {
      "expenseId" : 178,
      "amount" : 19.83,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.01.2022 12:22",
      "description" : ""
    }, {
      "expenseId" : 177,
      "amount" : 29.99,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "16.01.2022 00:18",
      "description" : "dla Waldiego"
    }, {
      "expenseId" : 176,
      "amount" : 95.69,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "15.01.2022 21:05",
      "description" : "recepta z czerwonej góry"
    }, {
      "expenseId" : 175,
      "amount" : 6.68,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "15.01.2022 20:58",
      "description" : "chusteczki nawilżające + rolka papieru toaletowego XD D"
    }, {
      "expenseId" : 186,
      "amount" : 150.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "15.01.2022 19:25",
      "description" : "nocleg u Waldiego"
    }, {
      "expenseId" : 171,
      "amount" : 3.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.01.2022 17:01",
      "description" : "bułka"
    }, {
      "expenseId" : 172,
      "amount" : 1200.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "15.01.2022 17:01",
      "description" : "łóżko rehabilitacyjne"
    }, {
      "expenseId" : 173,
      "amount" : 140.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "15.01.2022 17:01",
      "description" : "materac przeciw odleżynowy"
    }, {
      "expenseId" : 174,
      "amount" : 250.01,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "15.01.2022 17:01",
      "description" : "diesel dla T4, 5.94"
    }, {
      "expenseId" : 170,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.01.2022 11:10",
      "description" : ""
    }, {
      "expenseId" : 169,
      "amount" : 5.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.01.2022 23:17",
      "description" : "soczek"
    }, {
      "expenseId" : 168,
      "amount" : 47.49,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "14.01.2022 17:41",
      "description" : "Pampersy + sudocream"
    }, {
      "expenseId" : 167,
      "amount" : 84.81,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "13.01.2022 18:41",
      "description" : ""
    }, {
      "expenseId" : 166,
      "amount" : 64.08,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "13.01.2022 17:02",
      "description" : "apteka, Pampersy, podkłady"
    }, {
      "expenseId" : 165,
      "amount" : 72.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "13.01.2022 14:42",
      "description" : ""
    }, {
      "expenseId" : 164,
      "amount" : 22.7,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "13.01.2022 12:09",
      "description" : "dla Andrzeja"
    }, {
      "expenseId" : 163,
      "amount" : 4.2,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.01.2022 11:57",
      "description" : ""
    }, {
      "expenseId" : 161,
      "amount" : 28.06,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.01.2022 00:36",
      "description" : "hot dozki"
    }, {
      "expenseId" : 160,
      "amount" : 7.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.01.2022 00:28",
      "description" : ""
    }, {
      "expenseId" : 159,
      "amount" : 27.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "12.01.2022 23:49",
      "description" : "Internet"
    }, {
      "expenseId" : 162,
      "amount" : 150.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "12.01.2022 22:01",
      "description" : "nocleg u Vivaldiego"
    }, {
      "expenseId" : 158,
      "amount" : 17.65,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.01.2022 18:49",
      "description" : "na bułki orkiszowe"
    }, {
      "expenseId" : 157,
      "amount" : 250.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "11.01.2022 08:40",
      "description" : "wymiana oleju"
    }, {
      "expenseId" : 156,
      "amount" : 21.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.01.2022 18:36",
      "description" : "na rogaliki"
    }, {
      "expenseId" : 155,
      "amount" : 67.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "10.01.2022 15:20",
      "description" : "silver dragon\n"
    }, {
      "expenseId" : 154,
      "amount" : 3.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.01.2022 19:35",
      "description" : "sok"
    }, {
      "expenseId" : 153,
      "amount" : 17.69,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.01.2022 19:29",
      "description" : "na rynku, mleko, banany itp"
    }, {
      "expenseId" : 152,
      "amount" : 26.0,
      "categoryId" : 17,
      "categoryName" : "Kubek",
      "date" : "09.01.2022 15:25",
      "description" : "Tężnia Busko Zdrój"
    }, {
      "expenseId" : 151,
      "amount" : 108.6,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "08.01.2022 20:48",
      "description" : "3.19 gaz"
    }, {
      "expenseId" : 150,
      "amount" : 64.9,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "08.01.2022 20:09",
      "description" : "pizza 105 <3"
    }, {
      "expenseId" : 149,
      "amount" : 33.73,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.01.2022 13:18",
      "description" : "zakupy na rogaliki + mandarynki"
    }, {
      "expenseId" : 147,
      "amount" : 35.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "06.01.2022 20:15",
      "description" : "pizza"
    }, {
      "expenseId" : 146,
      "amount" : 34.99,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "04.01.2022 20:13",
      "description" : "nizoral"
    }, {
      "expenseId" : 145,
      "amount" : 9.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.01.2022 19:35",
      "description" : "ryż i kurczak"
    }, {
      "expenseId" : 144,
      "amount" : 26.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "03.01.2022 10:34",
      "description" : ""
    }, {
      "expenseId" : 143,
      "amount" : 1.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "02.01.2022 22:26",
      "description" : "Fantazje o Dziwotworach dla siostry\n\nPotęga Teraźniejszości\n\nWielki Reset"
    }, {
      "expenseId" : 142,
      "amount" : 152.08,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "01.01.2022 17:29",
      "description" : "3.13"
    }, {
      "expenseId" : 141,
      "amount" : 8.28,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.01.2022 16:45",
      "description" : "dzemorek i woda"
    }, {
      "expenseId" : 140,
      "amount" : 100.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "01.01.2022 16:14",
      "description" : "noworoczny obiad"
    }, {
      "expenseId" : 138,
      "amount" : 190.17,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "31.12.2021 15:12",
      "description" : ""
    }, {
      "expenseId" : 139,
      "amount" : 20.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "31.12.2021 15:12",
      "description" : ""
    }, {
      "expenseId" : 148,
      "amount" : 1357.5,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "31.12.2021 12:00",
      "description" : ""
    }, {
      "expenseId" : 137,
      "amount" : 122.75,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.12.2021 19:51",
      "description" : ""
    }, {
      "expenseId" : 136,
      "amount" : 91.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "27.12.2021 23:27",
      "description" : ""
    }, {
      "expenseId" : 135,
      "amount" : 46.67,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "27.12.2021 22:10",
      "description" : ""
    }, {
      "expenseId" : 134,
      "amount" : 9.68,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "26.12.2021 22:47",
      "description" : ""
    }, {
      "expenseId" : 133,
      "amount" : 58.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "26.12.2021 21:28",
      "description" : ""
    }, {
      "expenseId" : 132,
      "amount" : 11.38,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "25.12.2021 22:21",
      "description" : "świąteczny hot dog + sok jabłkowy z miętą XD"
    }, {
      "expenseId" : 131,
      "amount" : 7.38,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.12.2021 20:44",
      "description" : ""
    }, {
      "expenseId" : 130,
      "amount" : 8.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.12.2021 17:50",
      "description" : "kolacja świąteczna \nXD"
    }, {
      "expenseId" : 129,
      "amount" : 72.4,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "24.12.2021 11:37",
      "description" : "zakupy na sprzątanie"
    }, {
      "expenseId" : 128,
      "amount" : 70.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "23.12.2021 22:45",
      "description" : "lubianka pensjonat"
    }, {
      "expenseId" : 127,
      "amount" : 19.55,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "23.12.2021 14:15",
      "description" : "piwsko + Apap"
    }, {
      "expenseId" : 126,
      "amount" : 35.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "23.12.2021 13:32",
      "description" : "restauracja Balbinka w Starachowicach XD"
    }, {
      "expenseId" : 125,
      "amount" : 2.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "23.12.2021 10:44",
      "description" : "bułka"
    }, {
      "expenseId" : 124,
      "amount" : 4.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.12.2021 18:07",
      "description" : "zapalarka i bułka"
    }, {
      "expenseId" : 123,
      "amount" : 70.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "22.12.2021 16:18",
      "description" : "lubianka pensjonat"
    }, {
      "expenseId" : 122,
      "amount" : 79.98,
      "categoryId" : 7,
      "categoryName" : "Ubrania",
      "date" : "22.12.2021 15:51",
      "description" : "bokserki, skarpety"
    }, {
      "expenseId" : 121,
      "amount" : 2.2,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.12.2021 13:58",
      "description" : ""
    }, {
      "expenseId" : 120,
      "amount" : 20.48,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "22.12.2021 13:53",
      "description" : "kapsułki do prania i coś do picia"
    }, {
      "expenseId" : 119,
      "amount" : 4.77,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.12.2021 10:49",
      "description" : ""
    }, {
      "expenseId" : 118,
      "amount" : 17.49,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.12.2021 09:30",
      "description" : "szczotka do włosów XD"
    }, {
      "expenseId" : 117,
      "amount" : 120.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "22.12.2021 09:17",
      "description" : "hotel Paradiso"
    }, {
      "expenseId" : 116,
      "amount" : 42.37,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.12.2021 19:53",
      "description" : ""
    }, {
      "expenseId" : 115,
      "amount" : 2.5,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.12.2021 16:13",
      "description" : ""
    }, {
      "expenseId" : 114,
      "amount" : 60.43,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "21.12.2021 16:05",
      "description" : "merci dla Dominiki i Kuby\nmerci dla sąsiadki Moniki\njedzenie dla dziadka"
    }, {
      "expenseId" : 112,
      "amount" : 112.79,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "21.12.2021 15:46",
      "description" : ""
    }, {
      "expenseId" : 113,
      "amount" : 7.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "21.12.2021 15:46",
      "description" : ""
    }, {
      "expenseId" : 111,
      "amount" : 9.16,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "21.12.2021 14:00",
      "description" : ""
    }, {
      "expenseId" : 110,
      "amount" : 22.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "21.12.2021 11:00",
      "description" : "kebap galeria echo"
    }, {
      "expenseId" : 109,
      "amount" : 1.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "20.12.2021 21:52",
      "description" : "dwie piżamy oraz koszulka władca pierścieni"
    }, {
      "expenseId" : 108,
      "amount" : 25.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "20.12.2021 17:58",
      "description" : "merci za znaleziony portfel"
    }, {
      "expenseId" : 107,
      "amount" : 120.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "20.12.2021 17:57",
      "description" : "herbata\ntaca na sery\nkosmetyki"
    }, {
      "expenseId" : 106,
      "amount" : 110.47,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "20.12.2021 15:22",
      "description" : "3.16"
    }, {
      "expenseId" : 105,
      "amount" : 28.95,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "20.12.2021 14:18",
      "description" : "tutti Santi, prosciutto"
    }, {
      "expenseId" : 104,
      "amount" : 14.99,
      "categoryId" : 12,
      "categoryName" : "Elektronika",
      "date" : "20.12.2021 14:07",
      "description" : "kabel aux"
    }, {
      "expenseId" : 103,
      "amount" : 40.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "19.12.2021 21:43",
      "description" : "pizza!"
    }, {
      "expenseId" : 102,
      "amount" : 16.68,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "18.12.2021 23:28",
      "description" : ""
    }, {
      "expenseId" : 101,
      "amount" : 10.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "17.12.2021 15:03",
      "description" : "napiwek z żeberek"
    }, {
      "expenseId" : 100,
      "amount" : 46.33,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.12.2021 21:21",
      "description" : ""
    }, {
      "expenseId" : 99,
      "amount" : 200.14,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "16.12.2021 21:10",
      "description" : "gaz 3,26\nbenzyna 5.86"
    }, {
      "expenseId" : 98,
      "amount" : 6.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "16.12.2021 17:37",
      "description" : ""
    }, {
      "expenseId" : 97,
      "amount" : 60.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "15.12.2021 22:30",
      "description" : "moje konto i Janiny"
    }, {
      "expenseId" : 96,
      "amount" : 22.76,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.12.2021 20:11",
      "description" : ""
    }, {
      "expenseId" : 95,
      "amount" : 40.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "15.12.2021 09:56",
      "description" : ""
    }, {
      "expenseId" : 94,
      "amount" : 55.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "13.12.2021 10:57",
      "description" : "tagliatelle z Kuchni"
    }, {
      "expenseId" : 93,
      "amount" : 18.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.12.2021 20:00",
      "description" : "herbata, restauracja noworolski"
    }, {
      "expenseId" : 92,
      "amount" : 7.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.12.2021 14:36",
      "description" : ""
    }, {
      "expenseId" : 91,
      "amount" : 65.72,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.12.2021 14:27",
      "description" : ""
    }, {
      "expenseId" : 90,
      "amount" : 130.11,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.12.2021 12:00",
      "description" : "na Sieprawski kaloryfer i obiad"
    }, {
      "expenseId" : 89,
      "amount" : 154.89,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.12.2021 20:13",
      "description" : "Sieprawski Kaloryfer!"
    }, {
      "expenseId" : 88,
      "amount" : 7.68,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.12.2021 14:43",
      "description" : ""
    }, {
      "expenseId" : 87,
      "amount" : 9.99,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.12.2021 14:35",
      "description" : "bułka"
    }, {
      "expenseId" : 86,
      "amount" : 6.67,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "10.12.2021 08:01",
      "description" : ""
    }, {
      "expenseId" : 85,
      "amount" : 144.34,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "09.12.2021 22:43",
      "description" : "3.29"
    }, {
      "expenseId" : 84,
      "amount" : 11.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "09.12.2021 22:28",
      "description" : ""
    }, {
      "expenseId" : 83,
      "amount" : 25.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "09.12.2021 13:01",
      "description" : ""
    }, {
      "expenseId" : 82,
      "amount" : 9.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "09.12.2021 08:15",
      "description" : "sucharki"
    }, {
      "expenseId" : 81,
      "amount" : 32.47,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.12.2021 20:58",
      "description" : ""
    }, {
      "expenseId" : 80,
      "amount" : 30.95,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "07.12.2021 20:09",
      "description" : "prosciutto e funghi z tutti santi"
    }, {
      "expenseId" : 79,
      "amount" : 4.88,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "07.12.2021 20:07",
      "description" : ""
    }, {
      "expenseId" : 78,
      "amount" : 68.9,
      "categoryId" : 7,
      "categoryName" : "Ubrania",
      "date" : "07.12.2021 19:39",
      "description" : "na Sieprawski kaloryfer z Biga!"
    }, {
      "expenseId" : 77,
      "amount" : 52.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "07.12.2021 12:55",
      "description" : "moja resto bar"
    }, {
      "expenseId" : 76,
      "amount" : 26.5,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "06.12.2021 10:16",
      "description" : "silver dragon"
    }, {
      "expenseId" : 75,
      "amount" : 7.96,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.12.2021 18:42",
      "description" : "4x płyta cd"
    }, {
      "expenseId" : 74,
      "amount" : 33.03,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "05.12.2021 18:28",
      "description" : ""
    }, {
      "expenseId" : 73,
      "amount" : 8.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.12.2021 22:49",
      "description" : "hot dog 😍"
    }, {
      "expenseId" : 72,
      "amount" : 10.9,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.12.2021 20:12",
      "description" : "radlerki XD"
    }, {
      "expenseId" : 71,
      "amount" : 12.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.12.2021 18:14",
      "description" : "kości dla Azy"
    }, {
      "expenseId" : 70,
      "amount" : 5.58,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.12.2021 16:05",
      "description" : ""
    }, {
      "expenseId" : 69,
      "amount" : 142.85,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "04.12.2021 14:42",
      "description" : "3.42"
    }, {
      "expenseId" : 68,
      "amount" : 20.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "04.12.2021 13:23",
      "description" : "Istambuł kebab w galerii echo"
    }, {
      "expenseId" : 67,
      "amount" : 100.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "04.12.2021 12:54",
      "description" : "nocleg y Bartka"
    }, {
      "expenseId" : 66,
      "amount" : 17.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "03.12.2021 23:45",
      "description" : "Istambuł kebab, tortilla duża z kurczakiem"
    }, {
      "expenseId" : 65,
      "amount" : 12.17,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.12.2021 18:38",
      "description" : "plastry XD Lewiatan"
    }, {
      "expenseId" : 64,
      "amount" : 22.46,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "03.12.2021 12:58",
      "description" : "jedzenie"
    }, {
      "expenseId" : 62,
      "amount" : 2.19,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.12.2021 19:08",
      "description" : "sok jabłkowy\nkorona Kielce"
    }, {
      "expenseId" : 61,
      "amount" : 17.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "02.12.2021 18:38",
      "description" : "silver dragon, Kielce"
    }, {
      "expenseId" : 60,
      "amount" : 46.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "02.12.2021 13:03",
      "description" : "schabowy + pierogi"
    }, {
      "expenseId" : 59,
      "amount" : 23.84,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "01.12.2021 21:26",
      "description" : "papier toaletowy + drobne zakupy obok Starego Sadu"
    }, {
      "expenseId" : 58,
      "amount" : 73.77,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "01.12.2021 17:36",
      "description" : "Renault master, 6.09"
    }, {
      "expenseId" : 57,
      "amount" : 144.77,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "01.12.2021 12:35",
      "description" : "3.53"
    }, {
      "expenseId" : 63,
      "amount" : 1350.0,
      "categoryId" : 18,
      "categoryName" : "Mieszkanie",
      "date" : "30.11.2021 21:40",
      "description" : "Pychowicka 18G/6 Listopad 2021"
    }, {
      "expenseId" : 56,
      "amount" : 14.15,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.11.2021 21:22",
      "description" : ""
    }, {
      "expenseId" : 55,
      "amount" : 4.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "30.11.2021 19:21",
      "description" : "od Oli Dąbek do Norymberskiej"
    }, {
      "expenseId" : 54,
      "amount" : 34.99,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "30.11.2021 16:11",
      "description" : "nizoral"
    }, {
      "expenseId" : 53,
      "amount" : 65.54,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.11.2021 23:45",
      "description" : "Vileda, suszarka na ubrania"
    }, {
      "expenseId" : 52,
      "amount" : 120.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "29.11.2021 19:25",
      "description" : ""
    }, {
      "expenseId" : 51,
      "amount" : 49.34,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "29.11.2021 12:45",
      "description" : "Chińczyk, silver dragon"
    }, {
      "expenseId" : 50,
      "amount" : 110.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "28.11.2021 15:29",
      "description" : "restauracja by Marco w Zamek Niedzica"
    }, {
      "expenseId" : 49,
      "amount" : 33.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "28.11.2021 13:55",
      "description" : "Niedzica zamek"
    }, {
      "expenseId" : 48,
      "amount" : 59.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "27.11.2021 21:00",
      "description" : "pizza"
    }, {
      "expenseId" : 47,
      "amount" : 35.0,
      "categoryId" : 17,
      "categoryName" : "Kubek",
      "date" : "27.11.2021 19:40",
      "description" : "termy Bukowina Tatrzańska"
    }, {
      "expenseId" : 46,
      "amount" : 178.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "27.11.2021 19:37",
      "description" : "termy Bukowina Tatrzańska, ja i Ola"
    }, {
      "expenseId" : 45,
      "amount" : 50.36,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "26.11.2021 21:48",
      "description" : ""
    }, {
      "expenseId" : 44,
      "amount" : 101.0,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "26.11.2021 21:16",
      "description" : "żeberka, burger, napiwek 8 zl"
    }, {
      "expenseId" : 43,
      "amount" : 50.32,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "26.11.2021 11:24",
      "description" : "wieprzowina podwójnie pieczona\nsilver dragon"
    }, {
      "expenseId" : 42,
      "amount" : 29.0,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "25.11.2021 13:03",
      "description" : ""
    }, {
      "expenseId" : 41,
      "amount" : 5.18,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.11.2021 21:28",
      "description" : ""
    }, {
      "expenseId" : 40,
      "amount" : 60.0,
      "categoryId" : 16,
      "categoryName" : "Nauka",
      "date" : "24.11.2021 21:25",
      "description" : ""
    }, {
      "expenseId" : 39,
      "amount" : 2.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "24.11.2021 19:48",
      "description" : "parking "
    }, {
      "expenseId" : 38,
      "amount" : 54.97,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "23.11.2021 17:27",
      "description" : "Reno puren\npolopiryna\nD3 + K2 "
    }, {
      "expenseId" : 37,
      "amount" : 620.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "23.11.2021 16:28",
      "description" : "cewka, świece zapłonowe, przewody"
    }, {
      "expenseId" : 36,
      "amount" : 80.96,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "22.11.2021 21:53",
      "description" : ""
    }, {
      "expenseId" : 35,
      "amount" : 160.0,
      "categoryId" : 15,
      "categoryName" : "Rozrywka",
      "date" : "22.11.2021 18:02",
      "description" : "ps plus, 12 msc"
    }, {
      "expenseId" : 34,
      "amount" : 31.5,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "22.11.2021 10:38",
      "description" : "indyjska kuchnia\nTandoori Flame"
    }, {
      "expenseId" : 33,
      "amount" : 4.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "21.11.2021 12:53",
      "description" : ""
    }, {
      "expenseId" : 32,
      "amount" : 8.88,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "19.11.2021 21:51",
      "description" : "papier toaletowy"
    }, {
      "expenseId" : 31,
      "amount" : 75.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "18.11.2021 08:35",
      "description" : "wymiana opon\npłyn zimowy"
    }, {
      "expenseId" : 30,
      "amount" : 2.0,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "17.11.2021 22:22",
      "description" : "soczek"
    }, {
      "expenseId" : 29,
      "amount" : 250.0,
      "categoryId" : 14,
      "categoryName" : "Pomoc",
      "date" : "17.11.2021 22:20",
      "description" : "szlachetna paczka allegro"
    }, {
      "expenseId" : 28,
      "amount" : 143.69,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "17.11.2021 22:19",
      "description" : ""
    }, {
      "expenseId" : 27,
      "amount" : 55.0,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "17.11.2021 12:04",
      "description" : ""
    }, {
      "expenseId" : 26,
      "amount" : 30.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "16.11.2021 23:52",
      "description" : "konto mamy"
    }, {
      "expenseId" : 25,
      "amount" : 30.0,
      "categoryId" : 13,
      "categoryName" : "Telefon",
      "date" : "16.11.2021 23:51",
      "description" : "moje konto"
    }, {
      "expenseId" : 24,
      "amount" : 60.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "16.11.2021 15:58",
      "description" : "za to że Pan zamontował podnośnik"
    }, {
      "expenseId" : 22,
      "amount" : 82.99,
      "categoryId" : 12,
      "categoryName" : "Elektronika",
      "date" : "15.11.2021 19:46",
      "description" : "zasilacz do laptopa "
    }, {
      "expenseId" : 23,
      "amount" : 7.85,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "15.11.2021 19:46",
      "description" : "bułki, woda, ser"
    }, {
      "expenseId" : 21,
      "amount" : 19.9,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "15.11.2021 12:58",
      "description" : "Chińczyk"
    }, {
      "expenseId" : 20,
      "amount" : 257.6,
      "categoryId" : 11,
      "categoryName" : "Wypoczynek",
      "date" : "14.11.2021 23:20",
      "description" : "hotel u skowronków w bukowinie"
    }, {
      "expenseId" : 18,
      "amount" : 6.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "14.11.2021 21:15",
      "description" : "mleko 2x"
    }, {
      "expenseId" : 17,
      "amount" : 15.0,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "14.11.2021 18:43",
      "description" : "wino dla Jakuba"
    }, {
      "expenseId" : 16,
      "amount" : 31.29,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.11.2021 23:30",
      "description" : "Apap 2x\nhot dog"
    }, {
      "expenseId" : 15,
      "amount" : 38.74,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.11.2021 21:40",
      "description" : "ciąg dalszy rzeczy dla dziadka"
    }, {
      "expenseId" : 14,
      "amount" : 98.09,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "13.11.2021 20:09",
      "description" : "zakupy dla dziadka żeby sprzątać"
    }, {
      "expenseId" : 13,
      "amount" : 27.5,
      "categoryId" : 10,
      "categoryName" : "Prezent",
      "date" : "13.11.2021 19:19",
      "description" : "wino dla Dominiki Kiełek"
    }, {
      "expenseId" : 12,
      "amount" : 164.28,
      "categoryId" : 9,
      "categoryName" : "Paliwo",
      "date" : "13.11.2021 18:29",
      "description" : "3,46 za litr :("
    }, {
      "expenseId" : 11,
      "amount" : 36.19,
      "categoryId" : 8,
      "categoryName" : "Restauracja",
      "date" : "13.11.2021 16:16",
      "description" : "maxi pizza, Rafaello, 45 cm"
    }, {
      "expenseId" : 19,
      "amount" : 40.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "13.11.2021 11:20",
      "description" : "dla mechanika"
    }, {
      "expenseId" : 10,
      "amount" : 99.0,
      "categoryId" : 7,
      "categoryName" : "Ubrania",
      "date" : "13.11.2021 11:09",
      "description" : "spodnie"
    }, {
      "expenseId" : 9,
      "amount" : 169.99,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "13.11.2021 10:16",
      "description" : "podnośnik manualny, lewy przód"
    }, {
      "expenseId" : 8,
      "amount" : 7.0,
      "categoryId" : 6,
      "categoryName" : "Myjnia",
      "date" : "13.11.2021 06:22",
      "description" : "multimyjmia"
    }, {
      "expenseId" : 5,
      "amount" : 165.48,
      "categoryId" : 4,
      "categoryName" : "Leki",
      "date" : "12.11.2021 22:20",
      "description" : "travocort, stieprox"
    }, {
      "expenseId" : 6,
      "amount" : 26.02,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "12.11.2021 21:28",
      "description" : ""
    }, {
      "expenseId" : 3,
      "amount" : 7.98,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.11.2021 22:19",
      "description" : ""
    }, {
      "expenseId" : 4,
      "amount" : 4.28,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "11.11.2021 22:19",
      "description" : ""
    }, {
      "expenseId" : 7,
      "amount" : 170.0,
      "categoryId" : 5,
      "categoryName" : "Skoda",
      "date" : "10.11.2021 17:18",
      "description" : "katalizator, olej do auta"
    }, {
      "expenseId" : 2,
      "amount" : 73.48,
      "categoryId" : 2,
      "categoryName" : "Zakupy",
      "date" : "08.11.2021 18:33",
      "description" : ""
    }, {
      "expenseId" : 1,
      "amount" : 46.5,
      "categoryId" : 3,
      "categoryName" : "Obiad",
      "date" : "08.11.2021 12:04",
      "description" : ""
    } ]
""".trimIndent()


val ALL_EXPENSES_AS_JSON =
    "$prefix $EXPENSES $suffix"