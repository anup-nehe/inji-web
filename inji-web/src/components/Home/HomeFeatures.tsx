import React, { useState } from "react";
import { HomeFeatureItem } from "./HomeFeatureItem";
import { useTranslation } from "react-i18next";
import { IoArrowForwardCircleOutline, IoArrowBackCircleOutline } from "react-icons/io5";
 
export const HomeFeatures: React.FC = () => {
  const { t } = useTranslation("HomePage");
  const [currentFeature, setCurrentFeature] = useState(0);
  const totalFeatures = 5;
 
  const handleNext = () => {
    setCurrentFeature((prev) => (prev + 1) % totalFeatures);
  };
 
  const handlePrev = () => {
    setCurrentFeature((prev) => (prev - 1 + totalFeatures) % totalFeatures);
  };
 
  return (
    <div className="flex justify-center items-center flex-col">
      <div className="font-semibold text-3xl m-5">{t("Features.heading")}</div>
      <div className="font-extralight text-center">{t("Features.description1")}</div>
      <div className="font-extralight text-center mb-10">{t("Features.description2")}</div>
      <img src={require("../../assets/InjiWebPreview.png")} alt="Inji Web Preview" />
      <div className="flex flex-wrap gap-8 container mx-auto pb-3 md:pb-20 justify-center">
        {/* Render all features in desktop view */}
        <div className="hidden md:flex flex-wrap gap-8 justify-center">
          <HomeFeatureItem itemno={1} />
          <HomeFeatureItem itemno={2} />
          <HomeFeatureItem itemno={3} />
          <HomeFeatureItem itemno={4} />
          <HomeFeatureItem itemno={5} />
        </div>
        {/* Render single feature in mobile view with fixed size */}
        <div className="sm:hidden w-full flex justify-center">
            <HomeFeatureItem itemno={currentFeature + 1} />
          </div>
        </div>
      {/* Navigation buttons and pagination dots for mobile view */}
      <div className="flex justify-between w-full px-5 sm:hidden items-center">
        <div className="flex">
          <button onClick={handlePrev} className="bg-grey-300 p-1" aria-label="Previous feature">
            <IoArrowBackCircleOutline size={50} className="text-gray-500" />
          </button>
          <button onClick={handleNext} className="bg-grey-300  rounded mr-1" aria-label="Next feature">
            <IoArrowForwardCircleOutline size={50} className="text-gray-500" />
          </button>
        </div>
        <div className="flex items-center px-5">
          {Array.from({ length: totalFeatures }, (_, index) => (
            <span
              key={index}
              className={`w-2 h-2 rounded-md mx-1 transition duration-300 ${index === currentFeature ? 'scale-125 bg-gradient-to-r from-orange-500  to-purple-700 w-7 h-2 rounded-full' : 'bg-gray-300'}`}
            ></span>
          ))}
        </div>
      </div>
    </div>
  );
};