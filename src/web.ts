import { WebPlugin } from '@capacitor/core';

import type { BrotherPrinterPluginPlugin } from './definitions';

export class BrotherPrinterWeb
  extends WebPlugin
  implements BrotherPrinterPluginPlugin
{
  constructor() {
    super({
      name: 'BrotherPrinterPlugin',
      platforms: ['web']
    });
  }
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
  
  async getContacts(filter: string): Promise<{ results: any[] }> {
    console.log('filter: ', filter);
    return {
      results: [{
        firstName: 'Charlie',
        lastName: 'Maere',
        telephone: '123456'
      }]
    };
  }
}


