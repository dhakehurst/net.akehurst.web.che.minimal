/**
 * Copyright (c) 2015 D. David H. Akehurst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package org.eclipse.che.api.deploy;

import com.google.inject.AbstractModule;

import org.eclipse.che.api.account.server.AccountService;
import org.eclipse.che.api.analytics.AnalyticsModule;
import org.eclipse.che.api.auth.AuthenticationService;
import org.eclipse.che.api.auth.oauth.OAuthTokenProvider;
import org.eclipse.che.api.builder.BuilderAdminService;
import org.eclipse.che.api.builder.BuilderSelectionStrategy;
import org.eclipse.che.api.builder.BuilderService;
import org.eclipse.che.api.builder.LastInUseBuilderSelectionStrategy;
import org.eclipse.che.api.builder.internal.BuilderModule;
import org.eclipse.che.api.builder.internal.SlaveBuilderService;
import org.eclipse.che.api.core.rest.ApiInfoService;
import org.eclipse.che.api.core.rest.CoreRestModule;
import org.eclipse.che.api.factory.FactoryModule;
import org.eclipse.che.commons.schedule.executor.ScheduleModule;
import org.eclipse.che.vfs.impl.fs.LocalVirtualFileSystemRegistry;
import org.eclipse.che.api.project.server.BaseProjectModule;
import org.eclipse.che.api.runner.LastInUseRunnerSelectionStrategy;
import org.eclipse.che.api.runner.RunnerAdminService;
import org.eclipse.che.api.runner.RunnerSelectionStrategy;
import org.eclipse.che.api.runner.RunnerService;
import org.eclipse.che.api.runner.internal.RunnerModule;
import org.eclipse.che.api.runner.internal.SlaveRunnerService;
import org.eclipse.che.api.user.server.UserProfileService;
import org.eclipse.che.api.user.server.UserService;
import org.eclipse.che.api.vfs.server.VirtualFileSystemModule;
import org.eclipse.che.api.vfs.server.VirtualFileSystemRegistry;
import org.eclipse.che.api.workspace.server.WorkspaceService;
import org.eclipse.che.docs.DocsModule;
import org.eclipse.che.everrest.CodenvyAsynchronousJobPool;
import org.eclipse.che.everrest.ETagResponseFilter;
import org.eclipse.che.inject.DynaModule;
import org.eclipse.che.security.oauth.OAuthAuthenticationService;
import org.eclipse.che.security.oauth.OAuthAuthenticatorProvider;
import org.eclipse.che.security.oauth.OAuthAuthenticatorProviderImpl;
import org.eclipse.che.security.oauth.OAuthAuthenticatorTokenProvider;
import org.eclipse.che.vfs.impl.fs.LocalFSMountStrategy;
import org.eclipse.che.vfs.impl.fs.LocalFileSystemRegistryPlugin;
import org.eclipse.che.vfs.impl.fs.MappedDirectoryLocalFSMountStrategy;
import org.eclipse.che.vfs.impl.fs.VirtualFileSystemFSModule;
import org.eclipse.che.vfs.impl.fs.WorkspaceToDirectoryMappingService;
import org.everrest.core.impl.async.AsynchronousJobPool;
import org.everrest.core.impl.async.AsynchronousJobService;
import org.everrest.guice.PathKey;
import com.google.inject.multibindings.Multibinder;
import org.eclipse.che.api.project.server.type.BaseProjectType;
import org.eclipse.che.api.project.server.type.ProjectType;

@DynaModule
public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ApiInfoService.class);
        bind(AccountService.class);
        bind(AuthenticationService.class);
        bind(WorkspaceService.class);
        bind(ETagResponseFilter.class);
        bind(UserService.class);
        bind(UserProfileService.class);
        bind(LocalFileSystemRegistryPlugin.class);
        bind(LocalFSMountStrategy.class).to(MappedDirectoryLocalFSMountStrategy.class);
        bind(WorkspaceToDirectoryMappingService.class);
        
        
//        bind(BuilderSelectionStrategy.class).to(LastInUseBuilderSelectionStrategy.class);
//        bind(BuilderService.class);
//        bind(BuilderAdminService.class);
//        bind(SlaveBuilderService.class);
//
//        bind(RunnerSelectionStrategy.class).to(LastInUseRunnerSelectionStrategy.class);
//        bind(RunnerService.class);
//        bind(RunnerAdminService.class);
//        bind(SlaveRunnerService.class);

//needs ssh extension
//        bind(KeyService.class);
//        bind(SshKeyStore.class).to(UserProfileSshKeyStore.class);

//        bind(OAuthAuthenticationService.class);
//        bind(OAuthTokenProvider.class).to(OAuthAuthenticatorTokenProvider.class);
//        bind(OAuthAuthenticatorProvider.class).to(OAuthAuthenticatorProviderImpl.class);

        
        bind(AsynchronousJobPool.class).to(CodenvyAsynchronousJobPool.class);
        bind(new PathKey<>(AsynchronousJobService.class, "/async/{ws-id}")).to(AsynchronousJobService.class);
        bind(VirtualFileSystemRegistry.class).to(LocalVirtualFileSystemRegistry.class);
        Multibinder<ProjectType> projectTypeMultibinder = Multibinder.newSetBinder(binder(), ProjectType.class);
        projectTypeMultibinder.addBinding().to(BaseProjectType.class);


        install(new CoreRestModule());
        install(new AnalyticsModule());
        install(new BaseProjectModule());
        install(new BuilderModule());
        install(new RunnerModule());
        install(new VirtualFileSystemModule());
        install(new VirtualFileSystemFSModule());
        install(new FactoryModule());
        install(new ScheduleModule());
    }
}
