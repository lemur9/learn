import ImageView from "@/components/ImageView/index.vue";
import XtxSku from "@/components/XtxSku/index.vue";

export const xtxPlugin = {
  install(app) {
    app.component('XtxImageView', ImageView);
    app.component('XtxSku', XtxSku);
  },
};
