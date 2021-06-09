package data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    public static void main(String[] args) throws JsonProcessingException {
//        String[] s = new String[] {"AKTSUG~a1697~cp1083:~r55~V~1:d:p2", "AKTSUG~97d7fdf04136eb208ae897f06d383733"};
        ObjectMapper om = new ObjectMapper();
        StdClass std = new StdClass();
        String s = om.writeValueAsString(std);
        System.out.println(s);
    }

    private static class StdClass {
        public StdClass(String[] referenceIds, String answerId, String answerText) {
            this.referenceIds = referenceIds;
            this.answerId = answerId;
            this.answerText = answerText;
        }

        public StdClass() {
        }

        public String[] getReferenceIds() {
            return referenceIds;
        }

        public void setReferenceIds(String[] referenceIds) {
            this.referenceIds = referenceIds;
        }

        public String getAnswerId() {
            return answerId;
        }

        public void setAnswerId(String answerId) {
            this.answerId = answerId;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        String[] referenceIds = new String[] {"AKTSUG~a1697~cp1083:~r55~V~1:d:p2", "AKTSUG~97d7fdf04136eb208ae897f06d383733"};
        String answerId = "1";
        String answerText = "安居客第三方";
    }
}
