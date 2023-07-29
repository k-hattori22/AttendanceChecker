package jp.kobe_u.cs.daikibo.attendancechecker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="app.cardreader")
public class CardReaderConfiguration {
    //プロパティは外部ファイルからDIするが，ない場合は下記のデフォルト値を使う
    private String username="card";
    private String password="reader";
}
