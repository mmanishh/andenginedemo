package com.joshskeen.andengine;

import android.view.KeyEvent;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

/**
 * Created by manish on 10/18/2016.
 */
public class GameActivity extends BaseGameActivity {

    private Camera camera;
    private ResourcesManager resourcesManager;

    public static final int CAMERA_WIDTH = 720;
    public static final int CAMERA_HEIGHT = 480;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new Camera(0, 0, 800, 480);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(800, 480), this.camera);
        engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourcesManager = ResourcesManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);


    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                // load menu resources, create menu scene
                // set menu scene using scene manager
                // disposeSplashScene();
                // READ NEXT ARTICLE FOR THIS PART.

                SceneManager.getInstance().createMenuScene();
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }

    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions)
    {
        return new LimitedFPSEngine(pEngineOptions, 60);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
        }
        return false;
    }
}
