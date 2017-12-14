package com.yb.demo.other.java.designpattern.create;

/**
 * 抽象工厂模式  Dialog 集中管理  Fragment 集中管理
 * Created by yb on 2017/12/14.
 */
public class AbstractFactoryTest {

    //    Step 1
    public interface IDialog {
        void show();
    }

    public interface IDialogFactory {
        IDialog createDialog(int type, Object... obj);
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

    public static class LoadingDialog2 implements IDialog {

        @Override
        public void show() {
            System.out.println("Loading2.........");
        }
    }

    public static class ShareDialog2 implements IDialog {

        @Override
        public void show() {
            System.out.println("share2.........");
        }
    }

    public static class CommentDialog2 implements IDialog {

        @Override
        public void show() {
            System.out.println("Comment2.........");
        }
    }

    //    Step 3
    public static final int TYPE_LOADING = 0;
    public static final int TYPE_SHARE = 1;
    public static final int TYPE_COMMENT = 2;

    //    Step 4
    public static class DialogFactory implements IDialogFactory {

        @Override
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

    public static class DialogFactory2 implements IDialogFactory {

        @Override
        public IDialog createDialog(int type, Object... obj) {
            IDialog iDialog;
            switch (type) {
                case TYPE_LOADING:
                    iDialog = new LoadingDialog2();
                    break;
                case TYPE_SHARE:
                    iDialog = new ShareDialog2();
                    break;
                case TYPE_COMMENT:
                    iDialog = new CommentDialog2();
                    break;
                default:
                    iDialog = null;
            }
            return iDialog;
        }
    }

    //Step 5
    IDialogFactory dialogFactory = new DialogFactory();

    public void setDialogFactory(IDialogFactory dialogFactory) {
        this.dialogFactory = dialogFactory;
    }

    //Step 6
    public IDialog createDialog(int type, Object... obj) {
        return dialogFactory.createDialog(type, obj);
    }

}
