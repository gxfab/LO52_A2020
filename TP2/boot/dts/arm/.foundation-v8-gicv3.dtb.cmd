cmd_arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb := mkdir -p arch/arm64/boot/dts/arm/ ; aarch64-linux-gnu-gcc -E -Wp,-MD,arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.d.pre.tmp -nostdinc -I./scripts/dtc/include-prefixes -undef -D__DTS__ -x assembler-with-cpp -o arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.dts.tmp arch/arm64/boot/dts/arm/foundation-v8-gicv3.dts ; ./scripts/dtc/dtc -O dtb -o arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb -b 0 -iarch/arm64/boot/dts/arm/ -i./scripts/dtc/include-prefixes -Wno-unit_address_vs_reg -Wno-simple_bus_reg -Wno-unit_address_format -Wno-pci_bridge -Wno-pci_device_bus_num -Wno-pci_device_reg  -d arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.d.dtc.tmp arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.dts.tmp ; cat arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.d.pre.tmp arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.d.dtc.tmp > arch/arm64/boot/dts/arm/.foundation-v8-gicv3.dtb.d

source_arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb := arch/arm64/boot/dts/arm/foundation-v8-gicv3.dts

deps_arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb := \
  arch/arm64/boot/dts/arm/foundation-v8.dtsi \

arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb: $(deps_arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb)

$(deps_arch/arm64/boot/dts/arm/foundation-v8-gicv3.dtb):
