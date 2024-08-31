import React from "react";
import ReactDOM from "react-dom/client";
import { StorageHelper } from "./common/helpers/storage-helper";
import App from "./app";
import "@cloudscape-design/global-styles/index.css";
import {initializeStore} from "./islands/providers/zu-store.ts";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const store = initializeStore(); // 스토어 초기화

const theme = StorageHelper.getTheme();
StorageHelper.applyTheme(theme);

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
