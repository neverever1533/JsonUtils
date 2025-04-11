# JsonUtils
## 项目地址：
  https://github.com/neverever1533/JsonUtils

## 特性：
  - 支持JSON文件读写字符串处理；
  - 支持xml，HTML，JSON等特殊tag转义后存储；
    * 如xml中&<>转义为"\&amp;"、"\&lt;"、"\&gt;"需要；
    * properties存储键值对时"=<>冲突；
    * JSON书写规范的,":{}[]造成混乱等等；
  - 支持obj和array增删改查操作；

## tips：
  转义可使用JsonString类的tagReplaced(string)，恢复可tagOriginal(string)方法获取。
