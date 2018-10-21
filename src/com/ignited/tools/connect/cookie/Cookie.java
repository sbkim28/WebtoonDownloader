package com.ignited.tools.connect.cookie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Cookie {

    private String name;
    private String value;
    private String path;
    private String domain;
    private long expires;

    public Cookie(String setCookie) {
        String[] items = setCookie.split(";");
        List<Pair> list= cut(items);

        name = list.get(0).key;
        value = list.get(0).value;
        list.remove(0);
        boolean flag = false;
        for(Pair p : list){
            switch (p.key.toLowerCase()){
                case "path" :
                    path = p.value;
                    break;
                case "domain" :
                    domain = p.value;
                    break;
                case "expires" :
                    flag = true;
                    expires = parseExpires(p.value);
                    break;
                case "max-age" :
                    expires = Long.parseLong(p.value) + System.currentTimeMillis();
                    default:
                        break;
            }
        }
        if(!flag){
            expires = Long.MAX_VALUE;
        }
    }

    public Cookie(String name, String value, String path, String domain, long expires) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.domain = domain;
        this.expires = expires;
    }

    private long parseExpires(String expires){

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        String s = expires.substring(expires.indexOf(",") + 2, expires.lastIndexOf(" GMT"));

        for(int i = 0; i< months.length; ++i){
            if(s.contains(months[i])){
                String mon = i + 1 + "";
                if(mon.length() == 1) mon = "0" + mon;
                s = s.replace(months[i], mon);
            }
        }

        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        try {
            return format.parse(s).getTime();
        } catch (ParseException e) {
            throw new MalformedCookieException(e);
        }
    }

    public String getCookie(){
        return name + "=" + value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public long getExpires() {
        return expires;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    private List<Pair> cut(String[] items) {
        List<Pair> ret = new ArrayList<>();
        for (String s : items) {
            try {
                ret.add(new Pair(s.substring(0, s.indexOf("=")).trim(),
                        s.substring(s.indexOf("=") + 1, s.length()).trim()));
            }catch (StringIndexOutOfBoundsException e){
                ret.add(new Pair(s, null));
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", path='" + path + '\'' +
                ", domain='" + domain + '\'' +
                ", expires='" + expires + '\'' +
                '}';
    }

    class Pair {
        private String key;
        private String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}
