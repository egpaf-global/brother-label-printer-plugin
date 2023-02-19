import { registerPlugin } from '@capacitor/core';

import type { BrotherPrinterPluginPlugin } from './definitions';

const BrotherPrinter = registerPlugin<BrotherPrinterPluginPlugin>('BrotherPrinter', {
  web: () => import('./web').then(m => new m.BrotherPrinterWeb()),
});

export * from './definitions';
export { BrotherPrinter };
