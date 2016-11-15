#include <stdio.h>
#include "com_azavea_pdal_PointLayout.h"
#include "JavaPipeline.hpp"
#include "Accessors.hpp"
#include "ArrayList.hpp"

using libpdaljava::ArrayList;
using pdal::PointLayout;
using pdal::DimTypeList;
using pdal::DimType;

JNIEXPORT jobject JNICALL Java_com_azavea_pdal_PointLayout_dimTypes
  (JNIEnv *env, jobject obj)
{
    ArrayList arrayList = ArrayList(env);
    PointLayout *pl = getHandle<PointLayout>(env, obj);
    DimTypeList dimTypes = pl->dimTypes();

    jclass dtClass = env->FindClass("com/azavea/pdal/DimType");
    jmethodID dtCtor = env->GetMethodID(dtClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");

    jobject result = env->NewObject(arrayList.arrayClass, arrayList.arrayCtor, dimTypes.size());

    for (auto dt: dimTypes)
    {
        jstring id = env->NewStringUTF(pdal::Dimension::name(dt.m_id).c_str());
        jstring type = env->NewStringUTF(pdal::Dimension::interpretationName(dt.m_type).c_str());
        jobject element = env->NewObject(dtClass, dtCtor, id, type);

        env->CallBooleanMethod(result, arrayList.arrayAdd, element);
        env->DeleteLocalRef(element);
        env->DeleteLocalRef(type);
        env->DeleteLocalRef(id);
    }

    return result;
}

JNIEXPORT jobject JNICALL Java_com_azavea_pdal_PointLayout_findDimType
  (JNIEnv *env, jobject obj, jstring jstr)
{
    std::string fid = std::string(env->GetStringUTFChars(jstr, 0));
    PointLayout *pl = getHandle<PointLayout>(env, obj);
    DimType dt = pl->findDimType(fid);
    jstring id = env->NewStringUTF(pdal::Dimension::name(dt.m_id).c_str());
    jstring type = env->NewStringUTF(pdal::Dimension::interpretationName(dt.m_type).c_str());

    jclass dtClass = env->FindClass("com/azavea/pdal/DimType");
    jmethodID dtCtor = env->GetMethodID(dtClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
    jobject result = env->NewObject(dtClass, dtCtor, id, type);

    return result;
}

JNIEXPORT jlong JNICALL Java_com_azavea_pdal_PointLayout_dimSize
  (JNIEnv *env, jobject obj, jstring jstr)
{
    std::string fid = std::string(env->GetStringUTFChars(jstr, 0));
    PointLayout *pl = getHandle<PointLayout>(env, obj);

    return pl->dimSize(pl->findDim(fid));
}

JNIEXPORT jlong JNICALL Java_com_azavea_pdal_PointLayout_dimOffset
  (JNIEnv *env, jobject obj, jstring jstr)
{
    std::string fid = std::string(env->GetStringUTFChars(jstr, 0));
    PointLayout *pl = getHandle<PointLayout>(env, obj);

    return pl->dimOffset(pl->findDim(fid));
}

JNIEXPORT jlong JNICALL Java_com_azavea_pdal_PointLayout_pointSize
  (JNIEnv *env, jobject obj)
{
    PointLayout *pl = getHandle<PointLayout>(env, obj);
    return pl->pointSize();
}

JNIEXPORT void JNICALL Java_com_azavea_pdal_PointLayout_dispose
  (JNIEnv *env, jobject obj)
{
    PointLayout *pl = getHandle<PointLayout>(env, obj);
    setHandle<int>(env, obj, 0);
    delete pl;
}
