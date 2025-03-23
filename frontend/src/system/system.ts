import {createSystem, defaultConfig, defineConfig} from "@chakra-ui/react"
import { textStyles } from "../themes/theme.ts"

const config = defineConfig({
    theme: {
        textStyles,
    },
})

export default createSystem(defaultConfig, config)