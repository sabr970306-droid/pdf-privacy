package com.bigbossgames.widgets;

public final class StyleCatalog {
    private StyleCatalog(){}

    public static final String[] CATEGORIES = {
        "All","Clock","Photo","GIF","Weather","Calendar","Battery","XPanel","Screen Time","Life","DIY"
    };

    public static final class Style {
        public final String type;
        public final String name;
        public Style(String type,String name){this.type=type;this.name=name;}
        public boolean diy(){return name.startsWith("DIY");}
    }

    public static final Style[] ALL = new Style[] {
        
new Style("Photo","DEFAULT"),
new Style("XPanel","XPanelStyle3"),
new Style("Gif","Gif1"),
new Style("Clock","Text3"),
new Style("Clock","Realism3"),
new Style("Music","Music1"),
new Style("Battery","Battery1"),
new Style("WeatherClock","WeatherClock2"),
new Style("Weather","Weather3"),
new Style("Date","Date4"),
new Style("Distance","DEFAULT"),
new Style("Date","Date1"),
new Style("Date","Date2"),
new Style("Date","Date3"),
new Style("Date","Date5"),
new Style("Date","Date6"),
new Style("Calendar","CalendarStyle3"),
new Style("Music","Music2"),
new Style("Music","Music3"),
new Style("Clock","Simple1"),
new Style("Clock","Simple2"),
new Style("Clock","Simple3"),
new Style("Clock","Realism1"),
new Style("Clock","Realism2"),
new Style("Clock","Text1"),
new Style("Clock","Text2"),
new Style("Clock","Text4"),
new Style("Clock","WorldClock1"),
new Style("WeatherClock","WeatherClock1"),
new Style("WeatherClock","WeatherClock3"),
new Style("WeatherClock","WeatherClock4"),
new Style("WeatherClock","WeatherClock5"),
new Style("Gif","Gif2"),
new Style("Gif","Gif3"),
new Style("Gif","Gif4"),
new Style("Gif","Gif5"),
new Style("Gif","Gif6"),
new Style("Weather","Weather1"),
new Style("Weather","Weather2"),
new Style("Photo","Photo1"),
new Style("Photo","Photo7"),
new Style("Photo","Photo2"),
new Style("Photo","Photo8"),
new Style("Photo","Photo9"),
new Style("Photo","Photo3"),
new Style("Photo","Photo10"),
new Style("Photo","Photo4"),
new Style("Photo","Photo11"),
new Style("Photo","Photo12"),
new Style("Photo","Photo5"),
new Style("Photo","Photo14"),
new Style("Photo","Photo13"),
new Style("Photo","Photo6"),
new Style("Photo","Photo15"),
new Style("Photo","Photo16"),
new Style("Photo","Photo17"),
new Style("Photo","Photo18"),
new Style("Calendar","CalendarStyle1"),
new Style("Calendar","CalendarStyle2"),
new Style("Calendar","CalendarStyle4"),
new Style("Battery","Battery2"),
new Style("Battery","Battery3"),
new Style("Battery","Battery4"),
new Style("XPanel","XPanelStyle2"),
new Style("XPanel","XPanelStyle1"),
new Style("XPanel","XPanelStyle4"),
new Style("XPanel","XPanelStyle5"),
new Style("XPanel","XPanelStyle6"),
new Style("XPanel","XPanelStyle7"),
new Style("ScreenTime","ScreenTime1"),
new Style("ScreenTime","ScreenTime2"),
new Style("ScreenTime","ScreenTime3"),
new Style("ScreenTime","ScreenTime4"),
new Style("Photo","DIYPhoto1"),
new Style("Calendar","DIYCalendar1"),
new Style("Calendar","DIYCalendar2"),
new Style("Calendar","DIYCalendar3"),
new Style("Calendar","DIYCalendar4"),
new Style("Clock","DIYClock1"),
new Style("Clock","DIYClock2"),
new Style("Clock","DIYClock3"),
new Style("Clock","DIYClock4"),
new Style("Weather","DIYWeather1"),
new Style("Weather","DIYWeather2"),
new Style("Weather","DIYWeather3"),
new Style("Weather","DIYWeather4"),
new Style("XPanel","DIYXpanel1"),
new Style("XPanel","DIYXpanel2"),
new Style("XPanel","DIYXpanel3"),
new Style("XPanel","DIYXpanel4"),
new Style("XPanel","DIYXpanel5"),
new Style("XPanel","DIYXpanel6"),
new Style("XPanel","DIYXpanel7"),
new Style("Battery","DIYBattery1"),
new Style("Battery","DIYBattery2"),
new Style("Battery","DIYBattery3"),
new Style("Battery","DIYBattery4"),
new Style("ScreenTime","DIYScreenTime1"),
new Style("ScreenTime","DIYScreenTime2")

    };

    public static boolean inCategory(Style s,String category){
        if ("All".equals(category)) return true;
        if ("DIY".equals(category)) return s.diy();
        if ("GIF".equals(category)) return "Gif".equals(s.type);
        if ("Screen Time".equals(category)) return "ScreenTime".equals(s.type);
        if ("Clock".equals(category)) return "Clock".equals(s.type) || "WeatherClock".equals(s.type);
        if ("Weather".equals(category)) return "Weather".equals(s.type) || "WeatherClock".equals(s.type);
        if ("Life".equals(category)) {
            return "Photo".equals(s.type) || "Distance".equals(s.type) || "Date".equals(s.type)
                    || "Weather".equals(s.type) || "Calendar".equals(s.type) || "Music".equals(s.type);
        }
        return category.equals(s.type);
    }

    public static int palette(Style s){
        int h=(s.type+"|"+s.name).hashCode();
        if(h==Integer.MIN_VALUE)h=0;
        return Math.abs(h)%10;
    }

    public static String title(Style s){
        String n=s.name;
        if("DEFAULT".equals(n)) n=s.type+" Classic";
        n=n.replace("XPanelStyle","XPanel ").replace("CalendarStyle","Calendar ")
           .replace("WeatherClock","Weather Clock ").replace("ScreenTime","Screen Time ")
           .replace("DIY","DIY ").replace("Realism","Realism ").replace("Simple","Simple ")
           .replace("Text","Text ").replace("Photo","Photo ").replace("Battery","Battery ")
           .replace("Gif","GIF ").replace("Music","Music ").replace("Date","Date ")
           .replace("WorldClock","World Clock ").replace("Xpanel","XPanel ");
        return n.replaceAll("\\s+"," ").trim();
    }

    public static String subtitle(Style s){
        if("Clock".equals(s.type)) return "Live clock • 12/24h";
        if("WeatherClock".equals(s.type)) return "Clock + city";
        if("Photo".equals(s.type)) return "Choose your photo";
        if("Gif".equals(s.type)) return "Animated-style card";
        if("Weather".equals(s.type)) return "Weather-style widget";
        if("Calendar".equals(s.type)) return "Date & calendar";
        if("Battery".equals(s.type)) return "Live battery level";
        if("XPanel".equals(s.type)) return "Clock • battery • date";
        if("ScreenTime".equals(s.type)) return "Daily app usage";
        if("Music".equals(s.type)) return "Music shortcut";
        if("Date".equals(s.type)) return "Day • month • year";
        if("Distance".equals(s.type)) return "Distance-style widget";
        return s.type;
    }
}