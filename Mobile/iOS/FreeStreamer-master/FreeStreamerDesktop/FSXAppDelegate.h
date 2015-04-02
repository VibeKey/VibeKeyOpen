/*
 * This file is part of the FreeStreamer project,
 * (C)Copyright 2011-2015 Matias Muhonen <mmu@iki.fi>
 * See the file ''LICENSE'' for using the code.
 *
 * https://github.com/muhku/FreeStreamer
 */

#import <Cocoa/Cocoa.h>

/**
 * The application delegate of the OS X example application.
 */
@interface FSXAppDelegate : NSObject <NSApplicationDelegate>

/**
 * Reference to a window.
 */
@property (assign) IBOutlet NSWindow *window;

@end