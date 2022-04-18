package com.kang.kanglog.web.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

@Slf4j
public class PostReqDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Data
    public static class PostSaveReqDto {

        private String title;
        private String content;
        private String tags;

        public void refineContent(Document doc, List<String> imgUrlList, Elements imgs) {

            if (imgUrlList.size() == 0) return;
            int index = 0;
            for(Element element : imgs){
                //element.remove(); imgUrlList.get(index)
                element.tagName("img");
                element.attr("src", imgUrlList.get(index));
                //element.replaceWith(imgNode);
                index ++;
            }

            // img Tag 대체
            String outPutContent = doc.body().toString();
            log.info("outPutContent: "+outPutContent);
            this.content = outPutContent;
        }

    }


}
