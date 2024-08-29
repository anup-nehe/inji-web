import React, {useEffect, useState} from "react";
import {SpinningLoader} from "../components/Common/SpinningLoader";
import {api} from "../utils/api";
import {LandingPageWrapper} from "../components/Common/LandingPageWrapper";
import {ErrorSheildIcon} from "../components/Common/ErrorSheildIcon";
import {useTranslation} from "react-i18next";

export const AuthorizationPage: React.FC = () => {
    const url = window.location.href;
    const currentQueryParams = new URLSearchParams(window.location.search);
    const [error, setError] = useState<String>(currentQueryParams.get("error")+"");
    const {t} = useTranslation("AuthorizationPage");

    useEffect( () => {
        if(url.indexOf("error") === -1) {
            window.location.href = api.mimotoHost + "/authorize" + window.location.search;
        }
    },[])


    const getErrorCode = (errorCode: String) => {
        return t(`error.code.${errorCode}`);
    }

    const getErrorMessage = (errorCode: String) => {
        return t(`error.message.${errorCode}`);
    }


    if(url.indexOf("error") == -1){
        return <div><LandingPageWrapper icon={<SpinningLoader/>} title={""} subTitle={""} gotoHome={false}/></div>
    }
    return <div><LandingPageWrapper icon={<ErrorSheildIcon />} title={getErrorCode(error)} subTitle={getErrorMessage(error)} gotoHome={false}/></div>
}


