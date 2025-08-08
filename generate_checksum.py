#!/usr/bin/env python3
import hashlib
import base64
import sys
import os

def generate_apk_checksum(apk_path):
    """Generate SHA-256 checksum for APK file and return base64 encoded string"""
    if not os.path.exists(apk_path):
        print(f"Error: APK file not found at {apk_path}")
        return None
    
    with open(apk_path, 'rb') as f:
        apk_data = f.read()
    
    # Generate SHA-256 hash
    sha256_hash = hashlib.sha256(apk_data).digest()
    
    # Encode to base64
    base64_checksum = base64.b64encode(sha256_hash).decode('utf-8')
    
    return base64_checksum

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_checksum.py <path_to_apk>")
        print("Example: python generate_checksum.py app/build/outputs/apk/debug/app-debug.apk")
        sys.exit(1)
    
    apk_path = sys.argv[1]
    checksum = generate_apk_checksum(apk_path)
    
    if checksum:
        print(f"APK Checksum: {checksum}")
        print("\nUpdate your JSON with this checksum:")
        print(f'"android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM": "{checksum}"')
