package com.chuangjiangx.unipay.services.tts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpeakService extends Service implements SpeechSynthesizerListener {

    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";

    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final String EXTRA_TEXT = "EXTRA_TEXT";
    private static final String EXTRA_STOP = "EXTRA_STOP";
    private String mSampleDirPath;

    private SpeechSynthesizer mSpeechSynthesizer;

    @Override
    public void onCreate() {
        super.onCreate();
        initialEnv(getApplicationContext());
        initialTts(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String text = intent.getStringExtra(EXTRA_TEXT);
            boolean stop = intent.getBooleanExtra(EXTRA_STOP, false);
            batchSpeak(text, stop);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static void speak(Context context, String text) {
        Intent intent = new Intent(context, SpeakService.class);
        intent.putExtra(EXTRA_TEXT, text);
        context.startService(intent);
    }

    public static void stopAndSpeak(Context context, String text) {
        Intent intent = new Intent(context, SpeakService.class);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_STOP, true);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 播报实现
     */
    private void batchSpeak(String text, boolean stop) {
        if (mSpeechSynthesizer != null) {
            if (stop) {
                mSpeechSynthesizer.stop();
            }
            mSpeechSynthesizer.speak(text);
        }
//        List<SpeechSynthesizeBag> bags = new ArrayList<>();
//        bags.add(getSpeechSynthesizeBag( text));
//        int result = this.mSpeechSynthesizer.batchSpeak(bags);
//        if (result < 0) {
//        }
    }

    private SpeechSynthesizeBag getSpeechSynthesizeBag(String text) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        //需要合成的文本text的长度不能超过1024个GBK字节。
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId("5");
        return speechSynthesizeBag;
    }


    /**
     * 初始化百度语音环境
     */
    private void initialEnv(Context context) {
        String sdcardPath = context.getCacheDir().toString();
        mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        makeDir(mSampleDirPath);

        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        //copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    @SuppressWarnings("SameParameterValue")
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                fos = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initialTts(Context context) {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 文本模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        // this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
        //        + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        Properties pro = new Properties();
        String appid = null, apiKey = null, apiKeyT = null;
        try {
            AssetManager assets = getResources().getAssets();
            InputStream open = assets.open("voice.properties");

            pro.load(open);
            appid = pro.getProperty("APPID");
            apiKey = pro.getProperty("APIKEY");
            apiKeyT = pro.getProperty("APIKEYT");

            open.close();
            pro.clear();
        } catch (IOException ignored) {
        }


        mSpeechSynthesizer.setAppId(appid/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        mSpeechSynthesizer.setApiKey(apiKey, apiKeyT);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);

        if (!authInfo.isSuccess()) {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
        }

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);

        //打印引擎信息和model基本信息
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }
}
