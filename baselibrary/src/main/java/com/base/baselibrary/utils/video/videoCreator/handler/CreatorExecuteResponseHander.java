package com.base.baselibrary.utils.video.videoCreator.handler;

public interface CreatorExecuteResponseHander<Param> extends ResponseHandler {
    /**
     * on Success
     *
     * @param message complete output of the binary command
     */
    void onSuccess(Param message);

    /**
     * on Progress
     *
     * @param message current output of binary command
     */
    void onProgress(Param message);

    /**
     * on Failure
     *
     * @param message complete output of the binary command
     */
    void onFailure(Param message);
}
