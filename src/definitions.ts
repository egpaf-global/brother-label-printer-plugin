declare module "@capacitor/core" {
  interface PluginRegistry {
    BrotherPrinterPlugin: BrotherPrinterPluginPlugin;
  }
}

export interface BrotherPrinterPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  getContacts(filter: string): Promise<{results: any[]}>;
}
