package com.yb.demo.other.java.designpattern.create;

/**
 * 工厂模式  Dialog 集中管理  Fragment 集中管理
 * Created by yb on 2017/12/14.
 */
public class FactoryTest {

    //    Step 1
    public interface IDialog {
        void show();
    }

    // Step 2
    public static class LoadingDialog implements IDialog {

        @Override
        public void show() {
            System.out.println("Loading.........");
        }
    }

    public static class ShareDialog implements IDialog {

        @Override
        public void show() {
            System.out.println("share.........");
        }
    }

    public static class CommentDialog implements IDialog {

        @Override
        public void show() {
            System.out.println("Comment.........");
        }
    }

    //    Step 3

    public static final int TYPE_LOADING = 0;
    public static final int TYPE_SHARE = 1;
    public static final int TYPE_COMMENT = 2;

    //    Step 4
    public IDialog createDialog(int type, Object... obj) {
        IDialog iDialog;
        switch (type) {
            case TYPE_LOADING:
                iDialog = new LoadingDialog();
                break;
            case TYPE_SHARE:
                iDialog = new ShareDialog();
                break;
            case TYPE_COMMENT:
                iDialog = new CommentDialog();
                break;
            default:
                iDialog = null;
        }
        return iDialog;
    }
}
