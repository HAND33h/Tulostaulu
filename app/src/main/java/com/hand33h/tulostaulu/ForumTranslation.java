package com.hand33h.tulostaulu;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.translate.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.nl.translate.Translation;
import java.util.Locale;

/**
 * Opt-in translation for forum messages. Uses ML Kit on-device models.
 * No user messages are sent to an app-owned translation server.
 * Call translate when rendering received posts, not while composing drafts.
 */
public final class ForumTranslation {
    public interface Callback {
        void onSuccess(String text);
        void onError(String original, String reason);
    }
    private ForumTranslation() {}

    public static String preferredLanguage(Context context) {
        SharedPreferences p=context.getSharedPreferences("wos_tulostaulu",Context.MODE_PRIVATE);
        String selected=p.getString("lang","auto");
        if("auto".equals(selected)) selected=Locale.getDefault().getLanguage();
        String code=TranslateLanguage.fromLanguageTag(selected);
        return code==null?TranslateLanguage.ENGLISH:code;
    }

    public static void translate(Context context,String message,Callback callback) {
        if(message==null || message.trim().isEmpty()) {
            callback.onSuccess(message==null?"":message);
            return;
        }
        final String target=preferredLanguage(context);
        LanguageIdentification.getClient().identifyLanguage(message)
            .addOnSuccessListener(sourceTag->{
                String source=TranslateLanguage.fromLanguageTag(sourceTag);
                if(source==null) {
                    callback.onError(message,"Source language not supported");
                    return;
                }
                if(source.equals(target)) {
                    callback.onSuccess(message);
                    return;
                }
                Translator translator=Translation.getClient(new TranslatorOptions.Builder()
                    .setSourceLanguage(source).setTargetLanguage(target).build());
                DownloadConditions conditions=new DownloadConditions.Builder().requireWifi().build();
                translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(unused->translator.translate(message)
                        .addOnSuccessListener(translated->{translator.close();callback.onSuccess(translated);})
                        .addOnFailureListener(error->{translator.close();callback.onError(message,error.getMessage());}))
                    .addOnFailureListener(error->{translator.close();callback.onError(message,error.getMessage());});
            })
            .addOnFailureListener(error->callback.onError(message,error.getMessage()));
    }
}
