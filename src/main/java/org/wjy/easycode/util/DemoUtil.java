package org.wjy.easycode.util;

import org.wjy.easycode.config.SpringBeanUtil;
import org.wjy.easycode.dao.DemoDaoServiceImpl;

public class DemoUtil {

    // static方法内使用变量，变量必须也是static
//    private DemoDaoServiceImpl tagBean;
    private static DemoDaoServiceImpl tagBean;

    static {
        // 使用static代码块，注入静态对象
        tagBean = SpringBeanUtil.getBean(DemoDaoServiceImpl.class);
    }

    // 静态方法内使用service
    public static void hdlBean() {
        tagBean.insertByStatus(null);
    }
}
