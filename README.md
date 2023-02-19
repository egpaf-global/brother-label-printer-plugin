# cha-brother-label-printer

Capacitor Plugin for Borther Printer SDK

## Install

```bash
npm install cha-brother-label-printer
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`getContacts(...)`](#getcontacts)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getContacts(...)

```typescript
getContacts(filter: string) => Promise<{ results: any[]; }>
```

| Param        | Type                |
| ------------ | ------------------- |
| **`filter`** | <code>string</code> |

**Returns:** <code>Promise&lt;{ results: any[]; }&gt;</code>

--------------------

</docgen-api>
