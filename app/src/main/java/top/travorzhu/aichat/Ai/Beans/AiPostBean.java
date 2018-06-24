package top.travorzhu.aichat.Ai.Beans;

public class AiPostBean {
    private final int reqType = 0;
    private Perception perception;
    private UserInfo userInfo;

    public AiPostBean(String text, String city, String province, String street) {
        this.perception = new Perception(text, city, province, street);
        this.userInfo = new UserInfo();
    }

    public int getReqType() {
        return reqType;
    }

    public Perception getPerception() {
        return perception;
    }

    public void setPerception(Perception perception) {
        this.perception = perception;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setText(String text) {
        this.perception.getInputText().setText(text);
    }

    public void setLocation(String city, String province, String street) {
        this.getPerception().getSelfInfo().getLocation().setCity(city);
        this.getPerception().getSelfInfo().getLocation().setProvince(province);
        this.getPerception().getSelfInfo().getLocation().setStreet(street);
    }

    public void newLocation(String city, String province, String street) {
        this.getPerception().newLocation(city, province, street);
    }

    public class Perception {
        InputText inputText;
        SelfInfo selfInfo;

        public Perception(String text, String city, String province, String street) {
            this.inputText = new InputText(text);
            this.selfInfo = new SelfInfo(city, province, street);
        }

        public InputText getInputText() {
            return inputText;
        }

        public void setInputText(InputText inputText) {
            this.inputText = inputText;
        }

        public SelfInfo getSelfInfo() {
            return selfInfo;
        }

        public void setSelfInfo(SelfInfo selfInfo) {
            this.selfInfo = selfInfo;
        }

        public void newLocation(String city, String province, String street) {
            this.getSelfInfo().newLocation(city, province, street);
        }

        public class InputText {
            String text;

            public InputText(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public class SelfInfo {
            Location location;

            public SelfInfo(String city, String province, String street) {
                this.location = new Location(city, province, street);
            }

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public void newLocation(String city, String province, String street) {
                this.location = new Location(city, province, street);
            }

            public class Location {
                String city;
                String province;
                String street;

                public Location(String city, String province, String street) {
                    this.city = city;
                    this.province = province;
                    this.street = street;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }
            }
        }
    }

    class UserInfo {
        final String apiKey = "a2d42a8ef29e4251a6979061349bd68c";
        final String userId = "AiRobot";

        public String getApiKey() {
            return apiKey;
        }

        public String getUserId() {
            return userId;
        }
    }
}
