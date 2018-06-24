package top.travorzhu.aichat.Ai.Beans;

import java.util.List;

public class AiReciveBean {
    Intent intent;
    List<Results> results;
    Emotion emotion;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return "AiReciveBean{" +
                "intent=" + intent +
                ", result=" + results +
                '}';
    }

    public class Intent {
        String actionName;
        int code;
        String intentName;

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getIntentName() {
            return intentName;
        }

        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }

        @Override
        public String toString() {
            return "intent{" +
                    "actionName='" + actionName + '\'' +
                    ", code=" + code +
                    ", intentName='" + intentName + '\'' +
                    '}';
        }
    }

    public class Results {
        int groupType;
        String resultType;
        Values values;

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public Values getValues() {
            return values;
        }

        public void setValues(Values values) {
            this.values = values;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "groupType=" + groupType +
                    ", resultType='" + resultType + '\'' +
                    ", values=" + values +
                    '}';
        }

        public class Values {
            String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            @Override
            public String toString() {
                return "values{" +
                        "text='" + text + '\'' +
                        '}';
            }
        }
    }

    public class Emotion {
        RobotEmotion robotEmotion;
        UserEmotion userEmotion;

        public RobotEmotion getRobotEmotion() {
            return robotEmotion;
        }

        public void setRobotEmotion(RobotEmotion robotEmotion) {
            this.robotEmotion = robotEmotion;
        }

        public UserEmotion getUserEmotion() {
            return userEmotion;
        }

        public void setUserEmotion(UserEmotion userEmotion) {
            this.userEmotion = userEmotion;
        }

        private class RobotEmotion {
            int a;
            int d;
            int emotionID;
            int p;

            public int getA() {
                return a;
            }

            public void setA(int a) {
                this.a = a;
            }

            public int getD() {
                return d;
            }

            public void setD(int d) {
                this.d = d;
            }

            public int getEmotionID() {
                return emotionID;
            }

            public void setEmotionID(int emotionID) {
                this.emotionID = emotionID;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }
        }

        private class UserEmotion {
            int a;
            int d;
            int emotionID;
            int p;

            public int getA() {
                return a;
            }

            public void setA(int a) {
                this.a = a;
            }

            public int getD() {
                return d;
            }

            public void setD(int d) {
                this.d = d;
            }

            public int getEmotionID() {
                return emotionID;
            }

            public void setEmotionID(int emotionID) {
                this.emotionID = emotionID;
            }

            public int getP() {
                return p;
            }

            public void setP(int p) {
                this.p = p;
            }
        }
    }
}
