import { useLocalStorage } from "../hooks/use-local-storage";
import { Mode, applyMode } from "@cloudscape-design/global-styles";
import { NavigationPanelState } from "../types";

const PREFIX = "example-app";
const THEME_STORAGE_NAME = `${PREFIX}-theme`;
const NAVIGATION_PANEL_STATE_STORAGE_NAME = `${PREFIX}-navigation-panel-state`;

export abstract class StorageHelper {
  static getTheme() {
    const value = useLocalStorage.getItem(THEME_STORAGE_NAME) ?? Mode.Light;
    const theme = value === Mode.Dark ? Mode.Dark : Mode.Light;

    return theme;
  }

  static applyTheme(theme: Mode) {
    useLocalStorage.setItem(THEME_STORAGE_NAME, theme);
    applyMode(theme);

    document.documentElement.style.setProperty(
      "--app-color-scheme",
      theme === Mode.Dark ? "dark" : "light"
    );

    return theme;
  }

  static getNavigationPanelState(): NavigationPanelState {
    const value =
        useLocalStorage.getItem(NAVIGATION_PANEL_STATE_STORAGE_NAME) ??
      JSON.stringify({
        collapsed: true,
      });

    let state: NavigationPanelState | null = null;
    try {
      state = JSON.parse(value);
    } catch {
      state = {};
    }

    return state ?? {};
  }

  static setNavigationPanelState(state: Partial<NavigationPanelState>) {
    const currentState = this.getNavigationPanelState();
    const newState = { ...currentState, ...state };
    const stateStr = JSON.stringify(newState);
    useLocalStorage.setItem(NAVIGATION_PANEL_STATE_STORAGE_NAME, stateStr);

    return newState;
  }
}
